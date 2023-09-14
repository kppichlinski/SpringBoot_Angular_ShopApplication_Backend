package com.example.shop.user.order.model.dto;

import com.example.shop.user.order.model.Payment;
import com.example.shop.user.order.model.Shipment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InitOrder {

    private List<Shipment> shipments;
    private List<Payment> payments;
}
