# Construction Materials Management Application

A web application for managing construction materials, orders, and user accounts. Built with **Java Spring Boot** and **Thymeleaf**, this project provides features for both administrators and regular users, such as inventory management, cart functionality, and order processing.

---

## Features

### **For Admins**
- Add, edit, delete construction materials.
- View and filter materials by type, price, and stock.
- Manage user orders:
  - Confirm orders.
  - Delete orders.

### **For Users**
- Browse materials by categories such as **Thermal Insulation**, **Roofing**, and **Rainwater Systems**.
- Add materials to the cart with quantity options.
- Edit and remove items from the cart.
- Proceed to checkout.

---

## Technologies Used
- **Java Spring Boot**: Backend framework for handling API requests and database operations.
- **Thymeleaf**: Template engine for rendering dynamic HTML content.
- **H2 Database / MySQL**: Database for storing materials, users, and orders.
- **CSS**: Custom styles for a modern and responsive UI.

---

## Installation

### Prerequisites
- **Java 17+**
- **Maven**
- **MySQL** or another compatible database.
- Optional: **IntelliJ IDEA**, **Eclipse**, or another IDE.

### Steps to Set Up
1. Clone the repository:
   ```bash
   git clone https://github.com/Pletea-Marinescu-Valentin/construction-materials-app.git
   cd construction-materials-app
   ```
2. Install dependencies using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ``` bash
   mvn spring-boot:run
   ```
4. Access the application: Open your browser and go to http://localhost:8080.

### Project Structure
``` plaintext
src
├── main
│   ├── java/com/example/construction_materials
│   │   ├── controller        # Handles HTTP requests and views
│   │   ├── model             # Entity classes for database tables
│   │   ├── repository        # Interfaces for database operations
│   │   ├── service           # Business logic layer
│   │   └── ConstructionMaterialsApplication.java  # Main class
│   ├── resources
│   │   ├── templates         # Thymeleaf HTML templates
│   │   ├── static/CSS        # Stylesheets and images
│   │   └── application.properties  # Configuration file
└── test                      # Unit and integration tests
```
