package com.example.shop.admin.order.model;

import com.example.shop.admin.product.model.AdminProduct;
import com.example.shop.user.order.model.Shipment;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_row")
@Getter
public class AdminOrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "productId")
    private AdminProduct product;

    @OneToOne
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;

    private Long orderId;
    private int quantity;
    private BigDecimal price;

}
