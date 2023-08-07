package com.example.shop.user.cart.service;

import com.example.shop.user.common.model.Cart;
import com.example.shop.user.common.repository.CartItemRepository;
import com.example.shop.user.common.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartCleaningService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Scheduled(cron = "${app.cart.cleanup.expression}")
    public void cleanupOldCarts() {
        List<Cart> oldCarts = cartRepository.findByCreatedLessThan(LocalDateTime.now().minusDays(4));
        List<Long> ids = oldCarts.stream()
                .map(Cart::getId)
                .toList();
        log.info("Old carts number: " + oldCarts.size());
        if (!ids.isEmpty()) {
            cartItemRepository.deleteAllByCartIdIn(ids);
            cartRepository.deleteAllByIdIn(ids);
        }
    }
}
