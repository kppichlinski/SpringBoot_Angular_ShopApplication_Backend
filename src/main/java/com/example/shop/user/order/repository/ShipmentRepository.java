package com.example.shop.user.order.repository;

import com.example.shop.user.order.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
