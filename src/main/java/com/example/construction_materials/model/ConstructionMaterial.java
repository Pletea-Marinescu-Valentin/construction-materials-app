/** Class representing construction materials. */
package com.example.construction_materials.model;

import jakarta.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "constructionmaterial")
public class ConstructionMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for the material

    @NotBlank(message = "Name is mandatory")
    private String name; // Name of the construction material

    @NotBlank(message = "Type is mandatory")
    private String type; // Type of the construction material (e.g., wood, steel)

    @Min(value = 0, message = "Price must be a positive number")
    private double price; // Price of the material

    @Min(value = 0, message = "Stock must be a non-negative number")
    private int stock; // Stock available for this material

    private String image; // Path to the image representing the material

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
