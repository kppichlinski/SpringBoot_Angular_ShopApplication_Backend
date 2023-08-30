package com.example.shop.user.order.service;

import com.example.shop.user.common.model.Cart;
import com.example.shop.user.common.model.CartItem;
import com.example.shop.user.common.repository.CartItemRepository;
import com.example.shop.user.common.repository.CartRepository;
import com.example.shop.user.order.model.Order;
import com.example.shop.user.order.model.OrderRow;
import com.example.shop.user.order.model.OrderStatus;
import com.example.shop.user.order.model.Shipment;
import com.example.shop.user.order.model.dto.OrderDto;
import com.example.shop.user.order.model.dto.OrderSummary;
import com.example.shop.user.order.repository.OrderRepository;
import com.example.shop.user.order.repository.OrderRowRepository;
import com.example.shop.user.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();

        Order order = Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .build();
        Order newOrder = orderRepository.save(order);
        saveOrderRows(cart, newOrder.getId(), shipment);

        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteById(orderDto.getCartId());

        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }

    private BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElseThrow()
                .add(shipment.getPrice());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        List<OrderRow> orderRows = cart.getItems().stream()
                .map(cartItem ->
                        OrderRow.builder()
                                .quantity(cartItem.getQuantity())
                                .productId(cartItem.getProduct().getId())
                                .price(cartItem.getProduct().getPrice())
                                .orderId(orderId)
                                .build()
                ).toList();
        orderRowRepository.saveAll(orderRows);

        OrderRow orderRow = OrderRow.builder()
                .quantity(1)
                .price(shipment.getPrice())
                .shipmentId(shipment.getId())
                .orderId(orderId)
                .build();
        orderRowRepository.save(orderRow);
    }
}
