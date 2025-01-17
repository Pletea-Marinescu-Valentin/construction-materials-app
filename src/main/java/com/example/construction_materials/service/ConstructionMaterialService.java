/** Service class for managing operations on construction materials. */
package com.example.construction_materials.service;

import com.example.construction_materials.model.ConstructionMaterial;
import com.example.construction_materials.repository.ConstructionMaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstructionMaterialService {

    private final ConstructionMaterialRepository materialRepository;

    /**
     * Constructor for injecting dependencies.
     *
     * @param materialRepository Repository for construction materials.
     */
    public ConstructionMaterialService(ConstructionMaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Adds a new construction material to the repository after validation.
     *
     * @param material The construction material to add.
     * @throws IllegalArgumentException if any validation fails.
     */
    public void addMaterial(ConstructionMaterial material) {
        StringBuilder errorMessage = new StringBuilder();

        // Validate name
        if (material.getName() == null || material.getName().isBlank()) {
            errorMessage.append("Name is mandatory. ");
        }

        // Validate type
        if (material.getType() == null || material.getType().isBlank()) {
            errorMessage.append("Type is mandatory. ");
        }

        // Validate price
        if (material.getPrice() < 0) {
            errorMessage.append("Price cannot be negative. ");
        }

        // Validate stock
        if (material.getStock() < 0) {
            errorMessage.append("Stock cannot be negative.");
        }

        // If there are validation errors, throw an exception
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }

        // Save the material if validation passes
        materialRepository.save(material);
    }

    /**
     * Retrieves a construction material by its ID.
     *
     * @param id The ID of the material.
     * @return The material if found.
     * @throws RuntimeException if the material is not found.
     */
    public ConstructionMaterial getMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
    }

    /**
     * Updates an existing construction material.
     *
     * @param material The updated construction material.
     * @throws RuntimeException if the material does not exist.
     */
    public void updateMaterial(ConstructionMaterial material) {
        if (!materialRepository.existsById(material.getId())) {
            throw new RuntimeException("Material not found");
        }
        materialRepository.save(material);
    }

    /**
     * Deletes a construction material by its ID.
     *
     * @param id The ID of the material to delete.
     */
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    /**
     * Retrieves all construction materials.
     *
     * @return A list of all materials.
     */
    public List<ConstructionMaterial> getAllMaterials() {
        return materialRepository.findAll();
    }

    /**
     * Retrieves a paginated list of construction materials.
     *
     * @param page The page number (0-based).
     * @param size The number of items per page.
     * @return A page of materials.
     */
    public Page<ConstructionMaterial> getMaterialsPage(int page, int size) {
        return materialRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * Retrieves a filtered and paginated list of construction materials based on criteria.
     *
     * @param type The type of material to filter by (optional).
     * @param search A search term for material names (optional).
     * @param page The page number (0-based).
     * @param sort The sort order ("asc" for ascending, "desc" for descending).
     * @param minPrice The minimum price filter.
     * @param maxPrice The maximum price filter.
     * @return A page of filtered materials.
     */
    public Page<ConstructionMaterial> getFilteredMaterials(String type, String search, int page, String sort, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, 20, sort.equals("asc") ? Sort.by("price").ascending() : Sort.by("price").descending());

        if (!type.isEmpty() && !search.isEmpty()) {
            return materialRepository.findByTypeAndNameContainingIgnoreCaseAndPriceBetween(type, search, minPrice, maxPrice, pageable);
        } else if (!type.isEmpty()) {
            return materialRepository.findByTypeAndPriceBetween(type, minPrice, maxPrice, pageable);
        } else if (!search.isEmpty()) {
            return materialRepository.findByNameContainingIgnoreCaseAndPriceBetween(search, minPrice, maxPrice, pageable);
        } else {
            return materialRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        }
    }
}
