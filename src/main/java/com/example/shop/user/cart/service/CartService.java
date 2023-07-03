package com.example.shop.user.cart.service;

import com.example.shop.user.cart.model.Cart;
import com.example.shop.user.cart.model.CartItem;
import com.example.shop.user.cart.model.dto.CartProductDto;
import com.example.shop.user.cart.repository.CartRepository;
import com.example.shop.user.common.model.Product;
import com.example.shop.user.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Cart addProductToCart(Long id, CartProductDto cartProductDto) {
        Cart cart = getInitializedCart(id);
        cart.addProduct(CartItem.builder()
                .quantity(cartProductDto.quantity())
                .product(getProduct(cartProductDto.productId()))
                .cartId(cart.getId())
                .build());
        return cart;
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    private Cart getInitializedCart(Long id) {
        if (id == null || id <= 0) {
            return cartRepository.save(Cart.builder().created(LocalDateTime.now()).build());
        }
        return cartRepository.findById(id).orElseThrow();
    }
}
