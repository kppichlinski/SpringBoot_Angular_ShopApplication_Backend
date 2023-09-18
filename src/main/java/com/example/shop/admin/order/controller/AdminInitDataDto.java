package com.example.shop.admin.order.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AdminInitDataDto {

    private Map<String, String> orderStatuses;
}
