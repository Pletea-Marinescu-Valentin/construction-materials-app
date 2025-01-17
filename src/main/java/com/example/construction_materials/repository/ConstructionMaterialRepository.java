package com.example.construction_materials.repository;

import com.example.construction_materials.model.ConstructionMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConstructionMaterialRepository extends JpaRepository<ConstructionMaterial, Long> {
    Page<ConstructionMaterial> findByTypeAndNameContainingIgnoreCaseAndPriceBetween(String type, String name, double minPrice, double maxPrice, Pageable pageable);

    Page<ConstructionMaterial> findByTypeAndPriceBetween(String type, double minPrice, double maxPrice, Pageable pageable);

    Page<ConstructionMaterial> findByNameContainingIgnoreCaseAndPriceBetween(String name, double minPrice, double maxPrice, Pageable pageable);

    Page<ConstructionMaterial> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
}
