package com.example.shop.user.order.service;

import com.example.shop.user.order.model.Shipment;
import com.example.shop.user.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }
}
