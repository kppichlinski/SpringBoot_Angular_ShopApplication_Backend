package com.example.shop.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

    private String value;
    private boolean adminAccess;
}