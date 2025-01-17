/** Class representing orders placed by users. */
package com.example.construction_materials.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the order

    @Column(nullable = false)
    private BigDecimal totalPrice; // Total price of the order

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who placed the order

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now(); // Order creation date, default is current date and time

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items; // List of items in the order

    @Column(name = "status", nullable = false)
    private String status; // Status of the order (e.g., "Pending", "Confirmed", "Shipped")

    /**
     * Default constructor (required by JPA).
     */
    public Order() {}

    /**
     * Constructor for initializing an order with a user and status.
     *
     * @param user The user who placed the order.
     * @param status The initial status of the order.
     */
    public Order(User user, String status) {
        this.user = user;
        this.status = status;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
