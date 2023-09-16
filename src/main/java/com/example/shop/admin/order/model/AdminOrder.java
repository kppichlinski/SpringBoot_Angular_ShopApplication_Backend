package com.example.shop.admin.order.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
public class AdminOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AdminOrderStatus orderStatus;

    @OneToMany
    @JoinColumn(name = "orderId")
    private List<AdminOrderRow> orderRows;

    @OneToOne
    private AdminPayment payment;

    private BigDecimal grossValue;
    private String firstname;
    private String lastname;
    private String street;
    private String zipcode;
    private String city;
    private String email;
    private String phone;
    private LocalDateTime placeDate;
}
