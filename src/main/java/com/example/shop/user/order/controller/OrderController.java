package com.example.shop.user.order.controller;

import com.example.shop.user.order.model.dto.InitOrder;
import com.example.shop.user.order.model.dto.OrderDto;
import com.example.shop.user.order.model.dto.OrderSummary;
import com.example.shop.user.order.service.OrderService;
import com.example.shop.user.order.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;

    @PostMapping
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto) {
        return orderService.placeOrder(orderDto);
    }

    @GetMapping("/initData")
    public InitOrder initData() {
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .build();
    }
}

