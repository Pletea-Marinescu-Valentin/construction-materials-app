/** Class representing an item in the shopping cart. */
package com.example.construction_materials.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private ConstructionMaterial material;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    /**
     * Default constructor (required by JPA).
     */
    public CartItem() {}

    /**
     * Constructor for quick initialization of a cart item.
     *
     * @param material The material associated with the cart item.
     * @param quantity The quantity of the material.
     */
    public CartItem(ConstructionMaterial material, int quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ConstructionMaterial getMaterial() {
        return material;
    }

    public void setMaterial(ConstructionMaterial material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
