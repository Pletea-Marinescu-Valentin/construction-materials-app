package com.example.construction_materials.repository;

import com.example.construction_materials.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
