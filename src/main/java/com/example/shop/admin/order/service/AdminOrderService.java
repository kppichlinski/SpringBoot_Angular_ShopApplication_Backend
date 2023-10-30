package com.example.shop.admin.order.service;

import com.example.shop.admin.order.model.AdminOrder;
import com.example.shop.admin.order.model.AdminOrderLog;
import com.example.shop.admin.order.model.AdminOrderStatus;
import com.example.shop.admin.order.repository.AdminOrderLogRepository;
import com.example.shop.admin.order.repository.AdminOrderRepository;
import com.example.shop.user.common.mail.EmailClientService;
import com.example.shop.user.common.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepository orderRepository;
    private final AdminOrderLogRepository adminOrderLogRepository;
    private final EmailClientService emailClientService;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return orderRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending())
        );
    }

    public AdminOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void patchOrder(Long id, Map<String, String> values) {
        AdminOrder adminOrder = orderRepository.findById(id).orElseThrow();
        patchValues(adminOrder, values);
    }

    private void patchValues(AdminOrder adminOrder, Map<String, String> values) {
        String orderStatus = values.get("orderStatus");
        if (orderStatus != null) {
            processOrderStatusChange(adminOrder, orderStatus);
        }
    }

    private void processOrderStatusChange(AdminOrder adminOrder, String orderStatus) {
        AdminOrderStatus oldStatus = adminOrder.getOrderStatus();
        AdminOrderStatus newStatus = AdminOrderStatus.valueOf(orderStatus);
        if (oldStatus == newStatus) {
            return;
        }
        adminOrder.setOrderStatus(newStatus);
        logStatusChange(adminOrder.getId(), oldStatus, newStatus);
        sendEmailNotification(newStatus, adminOrder);
    }

    private void sendEmailNotification(AdminOrderStatus orderStatus, AdminOrder adminOrder) {
        if (AdminOrderStatus.PROCESSING.equals(orderStatus)) {
            sendEmail(adminOrder.getEmail(),
                    "Order " + adminOrder.getId() + " changed status to: " + orderStatus.getValue(),
                    AdminOrderEmailMessage.createProcessingEmailMessage(adminOrder.getId(), orderStatus));
        } else if (AdminOrderStatus.COMPLETED.equals(orderStatus)) {
            sendEmail(adminOrder.getEmail(),
                    "Order " + adminOrder.getId() + " has been completed.",
                    AdminOrderEmailMessage.createCompletedEmailMessage(adminOrder.getId(), orderStatus));
        } else if (AdminOrderStatus.REFUNDED.equals(orderStatus)) {
            sendEmail(adminOrder.getEmail(),
                    "Order " + adminOrder.getId() + " changed status to: " + orderStatus.getValue(),
                    AdminOrderEmailMessage.createRefundEmailMessage(adminOrder.getId(), orderStatus));
        }
    }

    private void sendEmail(String email, String subject, String content) {
        emailClientService.getInstance(EmailService.BEAN_NAME).send(email, subject, content);
    }

    private void logStatusChange(Long orderId, AdminOrderStatus oldStatus, AdminOrderStatus newStatus) {
        adminOrderLogRepository.save(AdminOrderLog.builder()
                .orderId(orderId)
                .created(LocalDateTime.now())
                .note("Status change from " + oldStatus.getValue() + " to " + newStatus.getValue())
                .build());
    }
}
