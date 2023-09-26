package com.example.shop.admin.order.service;

import com.example.shop.admin.order.model.AdminOrder;
import com.example.shop.admin.order.model.AdminOrderStatus;
import com.example.shop.admin.order.model.dto.AdminOrderStats;
import com.example.shop.admin.order.repository.AdminOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminOrderStatsService {

    private final AdminOrderRepository orderRepository;

    public AdminOrderStats getStats() {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = LocalDateTime.now();
        List<AdminOrder> orders = orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from, to, AdminOrderStatus.COMPLETED
        );

        Map<Integer, AdminOrderStatsValue> result = new TreeMap<>();
        for (int i = from.getDayOfMonth(); i <= to.getDayOfMonth(); i++) {
            result.put(i, aggregateValues(i, orders));
        }

        return AdminOrderStats.builder()
                .labels(result.keySet().stream().toList())
                .sales(result.values().stream().map(o -> o.sales).toList())
                .orders(result.values().stream().map(o -> o.orders).toList())
                .build();
    }

    private AdminOrderStatsValue aggregateValues(int i, List<AdminOrder> orders) {
        BigDecimal totalValue = BigDecimal.ZERO;
        Long orderCount = 0L;
        for (AdminOrder order : orders) {
            if (i == order.getPlaceDate().getDayOfMonth()) {
                totalValue = totalValue.add(order.getGrossValue());
                orderCount++;
            }
        }
        return new AdminOrderStatsValue(totalValue, orderCount);
    }

    private record AdminOrderStatsValue(BigDecimal sales, Long orders){}
}
