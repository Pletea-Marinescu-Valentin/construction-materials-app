package com.example.construction_materials.repository;

import com.example.construction_materials.model.Cart;
import com.example.construction_materials.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
