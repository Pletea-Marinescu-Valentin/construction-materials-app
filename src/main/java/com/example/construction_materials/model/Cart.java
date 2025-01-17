/** Class representing the shopping cart of users. */
package com.example.construction_materials.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    /**
     * Default constructor (required by JPA).
     */
    public Cart() {}

    /**
     * Constructor for initializing a cart with a specific user.
     *
     * @param user The user associated with this cart.
     */
    public Cart(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    /**
     * Calculates the total price of all items in the cart.
     *
     * @return The total price as a double.
     */
    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getMaterial().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Adds an item to the cart and sets the cart reference on the item.
     *
     * @param item The CartItem to add.
     */
    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
    }

    /**
     * Removes an item from the cart and clears the cart reference on the item.
     *
     * @param item The CartItem to remove.
     */
    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
    }
}
