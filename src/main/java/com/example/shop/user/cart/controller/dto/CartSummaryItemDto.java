package com.example.shop.user.cart.controller.dto;

import com.example.shop.user.product.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartSummaryItemDto {

    private Long id;
    private int quantity;
    private ProductDto product;
    private BigDecimal lineValue;
}
