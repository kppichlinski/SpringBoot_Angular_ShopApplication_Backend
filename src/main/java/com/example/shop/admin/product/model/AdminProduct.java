package com.example.shop.admin.product.model;

import com.example.shop.admin.product.model.type.AdminProductCurrency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AdminProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long categoryId;
    private String description;
    private String fullDescription;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;

    private String image;
    private String slug;
}
