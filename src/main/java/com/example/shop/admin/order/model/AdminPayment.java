package com.example.shop.admin.order.model;

import com.example.shop.user.order.model.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "payment")
@Getter
public class AdminPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private String name;
    private boolean defaultPayment;
    private String note;
}
