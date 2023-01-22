package com.example.shop.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Product {
    String name;
    String category;
    String description;
    BigDecimal price;
    String currency;
}
