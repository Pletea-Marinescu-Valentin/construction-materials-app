/** Class for managing operations on construction materials, including adding, editing, and deleting materials. */
package com.example.construction_materials.controller;

import com.example.construction_materials.model.ConstructionMaterial;
import com.example.construction_materials.repository.UserRepository;
import com.example.construction_materials.model.User;
import com.example.construction_materials.service.ConstructionMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/materials")
public class ConstructionMaterialController {

    private final ConstructionMaterialService service;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for ConstructionMaterialController.
     *
     * @param service The service handling material-related operations.
     * @param userRepository Repository for accessing user data.
     * @param passwordEncoder The encoder for handling secure passwords.
     */
    public ConstructionMaterialController(ConstructionMaterialService service, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles GET requests to retrieve and display filtered materials.
     *
     * @param type The type of material to filter by.
     * @param search Search query for materials.
     * @param page The page number for pagination.
     * @param sort The sort order (asc/desc).
     * @param minPrice The minimum price filter.
     * @param maxPrice The maximum price filter.
     * @param model The model to hold attributes for the view.
     * @return The appropriate view based on user role.
     */
    @GetMapping
    public String getFilteredMaterials(
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "99999") double maxPrice,
            Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

        if (username != null) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Page<ConstructionMaterial> materials = service.getFilteredMaterials(type, search, page, sort, minPrice, maxPrice);
            model.addAttribute("materials", materials.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", materials.getTotalPages());

            return "ADMIN".equals(user.getRole()) ? "materials" : "materials-user";
        }

        return "error"; // Return error page if user is not authenticated.
    }

    /**
     * Displays the form for adding a new material.
     *
     * @param model The model to hold attributes for the view.
     * @return The add-material form view.
     */
    @GetMapping("/add")
    public String showAddMaterialForm(Model model) {
        model.addAttribute("material", new ConstructionMaterial());
        return "add-material";
    }

    /**
     * Processes the form for adding a new material.
     *
     * @param material The material details to add.
     * @param model The model to hold attributes for the view.
     * @return A redirect to the materials list or the add-material view in case of errors.
     */
    @PostMapping
    public String addMaterial(@ModelAttribute ConstructionMaterial material, Model model) {
        try {
            service.addMaterial(material);
            return "redirect:/materials";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("material", material);
            return "add-material";
        }
    }

    /**
     * Displays the form for editing an existing material.
     *
     * @param id The ID of the material to edit.
     * @param model The model to hold attributes for the view.
     * @return The edit-material form view.
     */
    @GetMapping("/edit/{id}")
    public String showEditMaterialForm(@PathVariable("id") Long id, Model model) {
        ConstructionMaterial material = service.getMaterialById(id);
        model.addAttribute("material", material);
        return "edit-material";
    }

    /**
     * Processes the form for editing a material.
     *
     * @param id The ID of the material to edit.
     * @param material The updated material details.
     * @param result BindingResult for validation errors.
     * @param model The model to hold attributes for the view.
     * @return A redirect to the materials list or the edit-material view in case of errors.
     */
    @PostMapping("/edit/{id}")
    public String editMaterial(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute ConstructionMaterial material,
            BindingResult result,
            Model model) {

        StringBuilder errorMessage = new StringBuilder();
        if (material.getPrice() < 0) errorMessage.append("Price cannot be negative. ");
        if (material.getStock() < 0) errorMessage.append("Stock cannot be negative.");

        if (errorMessage.length() > 0) {
            model.addAttribute("error", errorMessage.toString());
            model.addAttribute("material", material);
            return "edit-material";
        }

        material.setId(id);
        service.updateMaterial(material);

        return "redirect:/materials";
    }

    /**
     * Displays the confirmation page for deleting a material.
     *
     * @param id The ID of the material to delete.
     * @param model The model to hold attributes for the view.
     * @return The delete confirmation view.
     */
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable("id") Long id, Model model) {
        ConstructionMaterial material = service.getMaterialById(id);
        model.addAttribute("material", material);
        return "materials-delete";
    }

    /**
     * Processes the deletion of a material.
     *
     * @param id The ID of the material to delete.
     * @return A redirect to the materials list.
     */
    @PostMapping("/delete/{id}")
    public String deleteMaterial(@PathVariable("id") Long id) {
        service.deleteMaterial(id);
        return "redirect:/materials";
    }

    /**
     * Handles login functionality (role-based redirection).
     *
     * @param username The provided username.
     * @param password The provided password.
     * @param model The model to hold attributes for the view.
     * @return A redirect based on the user's role or the login page in case of errors.
     */
    @PostMapping("/login")
    public String handleLogin(String username, String password, Model model) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return "ADMIN".equalsIgnoreCase(user.get().getRole()) ? "redirect:/materials" : "redirect:/materials-user";
        }

        model.addAttribute("error", "Invalid username or password");
        return "index";
    }
}
