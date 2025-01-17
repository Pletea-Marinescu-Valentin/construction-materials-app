/** Class for managing operations associated with the user's shopping cart. */
package com.example.construction_materials.controller;

import com.example.construction_materials.model.Cart;
import com.example.construction_materials.model.ConstructionMaterial;
import com.example.construction_materials.model.User;
import com.example.construction_materials.repository.UserRepository;
import com.example.construction_materials.service.CartService;
import com.example.construction_materials.service.ConstructionMaterialService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/materials-user/cart")
public class CartController {

    private final CartService cartService;
    private final ConstructionMaterialService materialService;
    private final UserRepository userRepository;

    /**
     * Constructor for CartController.
     *
     * @param cartService The service handling cart-related operations.
     * @param materialService The service managing construction materials.
     * @param userRepository Repository for accessing user data.
     */
    public CartController(CartService cartService, ConstructionMaterialService materialService, UserRepository userRepository) {
        this.cartService = cartService;
        this.materialService = materialService;
        this.userRepository = userRepository;
    }

    /**
     * Handles GET requests to view the user's cart.
     *
     * - Retrieves the authenticated user's cart.
     * - Adds the cart to the model for rendering in the view.
     *
     * @param model The model to hold attributes for the view.
     * @return The name of the cart view template.
     */
    @GetMapping
    public String viewCart(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getCartForUser(user);
        model.addAttribute("cart", cart); // Add the cart to the model.
        return "cart"; // Return the cart view template.
    }

    /**
     * Handles POST requests to add an item to the user's cart.
     *
     * - Retrieves the authenticated user and the specified material.
     * - Adds the material to the user's cart with the given quantity.
     *
     * @param materialId The ID of the material to add.
     * @param quantity The quantity of the material to add.
     * @return A redirect to the cart view.
     */
    @PostMapping("/add/{materialId}")
    public String addToCart(@PathVariable Long materialId, @RequestParam int quantity) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Retrieve the user and material
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ConstructionMaterial material = materialService.getMaterialById(materialId);

        // Add the material to the cart
        cartService.addToCart(user, material, quantity);

        return "redirect:/materials-user/cart"; // Redirect to the cart view.
    }

    /**
     * Handles POST requests to remove an item from the user's cart.
     *
     * - Retrieves the authenticated user.
     * - Removes the specified material from the user's cart.
     *
     * @param materialId The ID of the material to remove.
     * @return A redirect to the cart view.
     */
    @PostMapping("/remove/{materialId}")
    public String removeFromCart(@PathVariable Long materialId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Retrieve the user and remove the material from their cart
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartService.removeFromCart(user, materialId);

        return "redirect:/materials-user/cart"; // Redirect to the cart view.
    }

    /**
     * Handles POST requests to update the quantity of an item in the user's cart.
     *
     * - Retrieves the authenticated user.
     * - Updates the quantity of the specified material in the user's cart.
     *
     * @param materialId The ID of the material to update.
     * @param quantity The new quantity to set.
     * @return A redirect to the cart view.
     */
    @PostMapping("/edit/{materialId}")
    public String editCartItem(@PathVariable Long materialId, @RequestParam int quantity) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Retrieve the user and update the material quantity
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            cartService.updateItemQuantity(user, materialId, quantity);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            throw new RuntimeException("Failed to update cart item");
        }

        return "redirect:/materials-user/cart"; // Redirect to the cart view.
    }

    /**
     * Handles POST requests to perform the checkout operation.
     *
     * - Retrieves the authenticated user.
     * - Processes the checkout for the user's cart.
     *
     * @return A redirect to the cart view with a success flag.
     */
    @PostMapping("/checkout")
    public String checkout() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Perform checkout operation
        cartService.checkout(user);

        return "redirect:/materials-user/cart?success"; // Redirect with success flag.
    }
}
