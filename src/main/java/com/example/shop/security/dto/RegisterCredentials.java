package com.example.shop.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterCredentials {

    @Email
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;
}
