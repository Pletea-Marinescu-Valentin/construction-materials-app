/** Class representing items included in an order. */
package com.example.construction_materials.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the order item

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // The order to which this item belongs

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private ConstructionMaterial material; // The material associated with this order item

    @Column(nullable = false)
    private int quantity; // Quantity of the material in the order

    /**
     * Default constructor (required by JPA).
     */
    public OrderItem() {}

    /**
     * Constructor for initializing an order item.
     *
     * @param order The order to which this item belongs.
     * @param material The material associated with this item.
     * @param quantity The quantity of the material.
     */
    public OrderItem(Order order, ConstructionMaterial material, int quantity) {
        this.order = order;
        this.material = material;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
}
