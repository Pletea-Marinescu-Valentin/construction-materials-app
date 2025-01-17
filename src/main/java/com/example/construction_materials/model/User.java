/** Class representing the users in the application. */
package com.example.construction_materials.model;

import jakarta.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the user

    @Column(nullable = false, unique = true)
    private String username; // Username of the user, must be unique

    @Column(nullable = false)
    private String password; // Password for user authentication

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format")
    private String email; // Email address of the user, must be unique and follow a valid format

    @Column(nullable = false)
    private String role; // Role of the user (e.g., "USER", "ADMIN")

    private String resetToken; // Token used for password reset functionality

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Cart cart; // Shopping cart associated with the user

    /**
     * Default constructor (required by Hibernate).
     */
    public User() {
    }

    /**
     * Custom constructor for quick initialization.
     *
     * @param username The username for the user.
     */
    public User(String username) {
        this.username = username;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
