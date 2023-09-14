package com.example.shop.user.order.service;

import com.example.shop.user.common.mail.EmailClientService;
import com.example.shop.user.common.mail.EmailService;
import com.example.shop.user.common.model.Cart;
import com.example.shop.user.common.model.CartItem;
import com.example.shop.user.common.repository.CartItemRepository;
import com.example.shop.user.common.repository.CartRepository;
import com.example.shop.user.order.model.*;
import com.example.shop.user.order.model.dto.OrderDto;
import com.example.shop.user.order.model.dto.OrderSummary;
import com.example.shop.user.order.repository.OrderRepository;
import com.example.shop.user.order.repository.OrderRowRepository;
import com.example.shop.user.order.repository.PaymentRepository;
import com.example.shop.user.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();

        Order order = Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .payment(payment)
                .build();
        Order newOrder = orderRepository.save(order);
        saveOrderRows(cart, newOrder.getId(), shipment);

        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteById(orderDto.getCartId());

        log.info("Order placed");

        emailClientService.getInstance(EmailService.BEAN_NAME)
                .send(orderDto.getEmail(), "Your order has been received", createEmailMessage(order));

        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(payment)
                .build();
    }

    private String createEmailMessage(Order order) {
        return "Your order with id: " + order.getId() +
                "\nOrder date: " + order.getPlaceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\nGross value: " + order.getGrossValue() + " PLN" +
                "\n\n" +
                "\nPayment: " + order.getPayment().getName() +
                ((order.getPayment().getNote() != null) ? "\n" + order.getPayment().getNote() : "") +
                "\n\nThanks for shopping.";
    }

    private BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElseThrow()
                .add(shipment.getPrice());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        List<OrderRow> orderRows = cart.getItems().stream()
                .map(cartItem ->
                        OrderRow.builder()
                                .quantity(cartItem.getQuantity())
                                .productId(cartItem.getProduct().getId())
                                .price(cartItem.getProduct().getPrice())
                                .orderId(orderId)
                                .build()
                ).toList();
        orderRowRepository.saveAll(orderRows);

        OrderRow orderRow = OrderRow.builder()
                .quantity(1)
                .price(shipment.getPrice())
                .shipmentId(shipment.getId())
                .orderId(orderId)
                .build();
        orderRowRepository.save(orderRow);
    }
}
