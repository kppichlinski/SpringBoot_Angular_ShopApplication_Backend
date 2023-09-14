package com.example.shop.user.order.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private String name;
    private boolean defaultPayment;
    private String note;
}
