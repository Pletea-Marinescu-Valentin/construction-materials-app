/** Class for managing the display of construction materials for users. */
package com.example.construction_materials.controller;

import com.example.construction_materials.model.ConstructionMaterial;
import com.example.construction_materials.model.User;
import com.example.construction_materials.repository.UserRepository;
import com.example.construction_materials.service.ConstructionMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/materials-user")
public class UserMaterialController {

    private final ConstructionMaterialService service;
    private final UserRepository userRepository;

    /**
     * Constructor for UserMaterialController.
     *
     * @param service The service handling material-related operations.
     * @param userRepository Repository for accessing user data.
     */
    public UserMaterialController(ConstructionMaterialService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    /**
     * Handles GET requests to retrieve and display materials for users.
     *
     * - Filters materials based on type, search query, price range, and sort order.
     * - Supports pagination for displaying results.
     * - Retrieves user-specific details for personalization (if needed in future expansions).
     *
     * @param type The type of material to filter by.
     * @param search Search query for materials.
     * @param page The page number for pagination.
     * @param sort The sort order (asc/desc).
     * @param minPrice The minimum price filter.
     * @param maxPrice The maximum price filter.
     * @param model The model to hold attributes for the view.
     * @return The materials-user view for displaying the materials.
     */
    @GetMapping
    public String getUserMaterials(
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "99999") double maxPrice,
            Model model) {

        // Retrieve details of the currently authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

        if (username != null) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Fetch filtered materials based on query parameters
            Page<ConstructionMaterial> materials = service.getFilteredMaterials(type, search, page, sort, minPrice, maxPrice);

            // Add attributes to the model for rendering in the view
            model.addAttribute("materials", materials.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", materials.getTotalPages());
        }

        return "materials-user"; // View template for displaying materials to users
    }
}
