package com.example.shop.product.controller;

import com.example.shop.product.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        return List.of(
                new Product("Nowy produkt 1", "Nowa kategoria 1", "Nowy opis 1", new BigDecimal("11.99"), "PLN"),
                new Product("Nowy produkt 2", "Nowa kategoria 2", "Nowy opis 2", new BigDecimal("12.99"), "PLN"),
                new Product("Nowy produkt 3", "Nowa kategoria 3", "Nowy opis 3", new BigDecimal("13.99"), "PLN"),
                new Product("Nowy produkt 4", "Nowa kategoria 4", "Nowy opis 4", new BigDecimal("14.99"), "PLN"),
                new Product("Nowy produkt 5", "Nowa kategoria 5", "Nowy opis 5", new BigDecimal("15.99"), "PLN")
        );
    }
}
