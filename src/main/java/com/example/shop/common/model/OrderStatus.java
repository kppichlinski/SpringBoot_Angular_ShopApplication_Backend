package com.example.shop.common.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("New"),
    PAID("Paid"),
    PROCESSING("Processing"),
    WAITING_FOR_DELIVERY("Waiting for delivery"),
    COMPLETED("Completed"),
    CANCELED("Cancelled"),
    REFUNDED("Refunded");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
