/** Class for managing administrative operations related to orders. */
package com.example.construction_materials.controller;

import com.example.construction_materials.model.Order;
import org.springframework.ui.Model;
import com.example.construction_materials.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/materials")
public class AdminController {

    private final OrderRepository orderRepository;

    /**
     * Constructor for AdminController.
     *
     * @param orderRepository The repository used for accessing and managing orders.
     */
    public AdminController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Handles GET requests to "/orders".
     *
     * - Fetches all orders from the database.
     * - Adds the list of orders to the model to display them in the view.
     *
     * @param model The model to add attributes for the view.
     * @return The name of the view displaying the orders.
     */
    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll()); // Add all orders to the model.
        return "orders"; // Return the "orders" view template.
    }

    /**
     * Handles POST requests to "/confirmOrder".
     *
     * - Confirms an order by changing its status to "Confirmed".
     * - Saves the updated order to the database.
     * - Redirects to the orders page after updating.
     *
     * @param orderId The ID of the order to confirm.
     * @return A redirect to the orders page.
     */
    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam Long orderId) {
        // Fetch the order by ID or throw an exception if not found.
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("Confirmed"); // Update the order's status.
        orderRepository.save(order); // Save the updated order.
        return "redirect:/materials/orders"; // Redirect to the orders page.
    }

    /**
     * Handles POST requests to "/deleteOrder".
     *
     * - Deletes an order from the database by its ID.
     * - Redirects to the orders page after deletion.
     *
     * @param orderId The ID of the order to delete.
     * @return A redirect to the orders page.
     */
    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam Long orderId) {
        orderRepository.deleteById(orderId); // Delete the order by its ID.
        return "redirect:/materials/orders"; // Redirect to the orders page.
    }

}
