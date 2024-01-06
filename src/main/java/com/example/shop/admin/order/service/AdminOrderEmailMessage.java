package com.example.shop.admin.order.service;

import com.example.shop.common.model.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminOrderEmailMessage {

    public static String createProcessingEmailMessage(Long id, OrderStatus orderStatus) {
        return "Your order: " + id + " is being processed." +
                "\nStatus has been changed to: " + orderStatus.getValue() + "." +
                "\nYour order is being processed by our employees." +
                "\nAfter completing we will pass it to delivery immediately." +
                "\nBest regards," +
                "\nShop";
    }

    public static String createCompletedEmailMessage(Long id, OrderStatus orderStatus) {
        return "Your order: " + id + " is being processed." +
                "\nStatus has been changed to: " + orderStatus.getValue() + "." +
                "\nTanks for shopping and welcome again." +
                "\nBest regards," +
                "\nShop";
    }

    public static String createRefundEmailMessage(Long id, OrderStatus orderStatus) {
        return "Your order: " + id + " is being processed." +
                "\nStatus has been changed to: " + orderStatus.getValue() + "." +
                "\nBest regards," +
                "\nShop";
    }
}
