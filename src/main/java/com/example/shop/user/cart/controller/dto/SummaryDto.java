package com.example.shop.user.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class SummaryDto {
    private BigDecimal grossValue;
}
