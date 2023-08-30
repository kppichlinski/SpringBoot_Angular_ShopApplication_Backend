package com.example.shop.user.order.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private boolean defaultShipment;

    @Enumerated(EnumType.STRING)
    private ShipmentType type;
}
