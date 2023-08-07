package com.example.shop.user.cart.service;

import com.example.shop.user.cart.model.dto.CartProductDto;
import com.example.shop.user.common.model.Cart;
import com.example.shop.user.common.model.Product;
import com.example.shop.user.common.repository.CartRepository;
import com.example.shop.user.common.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldAddProductToCartWhenCartIdNotExists() {
        // given
        Long cartId = 0L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        when(productRepository.findById(cartProductDto.productId())).
                thenReturn(Optional.of(Product.builder().id(cartProductDto.productId()).build()));
        when(cartRepository.save(any())).thenReturn(Cart.builder().id(1L).build());
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldAddProductToCartWhenCartIdExists() {
        // given
        Long cartId = 1L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        when(productRepository.findById(cartProductDto.productId())).
                thenReturn(Optional.of(Product.builder().id(cartProductDto.productId()).build()));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(Cart.builder().id(cartId).build()));
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}