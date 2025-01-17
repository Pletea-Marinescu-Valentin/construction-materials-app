/** Class for managing shopping cart operations and the checkout process. */
package com.example.construction_materials.service;

import com.example.construction_materials.model.*;
import com.example.construction_materials.repository.CartItemRepository;
import com.example.construction_materials.repository.CartRepository;
import com.example.construction_materials.repository.OrderRepository;
import com.example.construction_materials.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves or creates a shopping cart for the specified user.
     *
     * @param user The user for whom the cart is retrieved.
     * @return The user's cart.
     */
    public Cart getCartForUser(User user) {
        return cartRepository.findByUser(user).orElse(new Cart(user));
    }

    /**
     * Adds a construction material to the user's cart.
     *
     * @param user The user adding the item.
     * @param material The material to be added.
     * @param quantity The quantity to add.
     */
    public void addToCart(User user, ConstructionMaterial material, int quantity) {
        // Retrieve the persistent user from the database
        User persistentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve or create the cart for the user
        Cart cart = cartRepository.findByUser(persistentUser).orElseGet(() -> {
            Cart newCart = new Cart(persistentUser);
            cartRepository.save(newCart);
            return newCart;
        });

        // Check if the material already exists in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getMaterial().equals(material))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update the quantity of the existing item
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Create a new cart item
            CartItem newItem = new CartItem(material, quantity);
            newItem.setName(material.getName());
            newItem.setPrice(BigDecimal.valueOf(material.getPrice()));
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        // Save the updated cart
        cartRepository.save(cart);
    }

    /**
     * Removes a material from the user's cart.
     *
     * @param user The user whose cart is being modified.
     * @param materialId The ID of the material to remove.
     */
    public void removeFromCart(User user, Long materialId) {
        // Retrieve the user's cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find the item to remove
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getMaterial().getId().equals(materialId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        // Remove the item and save the updated cart
        cart.getItems().remove(itemToRemove);
        cartRepository.save(cart);
    }

    /**
     * Updates the quantity of a material in the user's cart.
     *
     * @param user The user whose cart is being modified.
     * @param materialId The ID of the material to update.
     * @param quantity The new quantity for the material.
     */
    public void updateItemQuantity(User user, Long materialId, int quantity) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getMaterial().getId().equals(materialId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);
        cartRepository.save(cart);
    }

    /**
     * Processes the checkout for the user's cart, creating an order and clearing the cart.
     *
     * @param user The user performing the checkout.
     */
    public void checkout(User user) {
        // Retrieve the persistent user
        User persistentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the user's cart
        Cart cart = cartRepository.findByUser(persistentUser)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Create a new order
        Order order = new Order();
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");
        order.setUser(user);

        orderRepository.save(order);

        // Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
