package com.example.shop.user.order.model.dto;

import com.example.shop.common.model.OrderStatus;
import com.example.shop.user.order.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OrderSummary {

    private Long id;
    private LocalDateTime placeDate;
    private OrderStatus status;
    private BigDecimal grossValue;
    private Payment payment;
}
