/** Class for managing user authentication and registration. */
package com.example.construction_materials.controller;

import com.example.construction_materials.model.User;
import com.example.construction_materials.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthController.
     *
     * @param userService The service handling user-related operations.
     * @param passwordEncoder The password encoder for secure password handling.
     */
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles GET requests to "/login".
     *
     * @return The login page template name.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // Login page
    }

    /**
     * Handles GET requests to "/signup".
     *
     * @return The signup page template name.
     */
    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup"; // Signup page
    }

    /**
     * Handles POST requests to "/signup".
     *
     * - Validates the password and confirmation password.
     * - Creates a new user with the default role "USER".
     * - Displays success or error messages based on the operation result.
     *
     * @param username The username provided by the user.
     * @param email The email provided by the user.
     * @param password The password provided by the user.
     * @param confirmPassword The confirmation password.
     * @param model The model to hold attributes for the view.
     * @return The view name to display.
     */
    @PostMapping("/signup")
    public String handleSignUp(String username, String email, String password, String confirmPassword, Model model) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup"; // Reload the signup form with an error message
        }
        try {
            // Create the user with the default role "USER"
            userService.createUser(username, email, password, "USER");
            model.addAttribute("success", "Account created successfully! Please log in.");
            return "redirect:/login"; // Redirect to login page
        } catch (Exception e) {
            model.addAttribute("error", "Error creating account: " + e.getMessage());
            return "signup"; // Reload the signup form with an error message
        }
    }

    /**
     * Handles POST requests to "/login".
     *
     * - Validates the user credentials.
     * - Redirects users to the appropriate page based on their role.
     * - Displays an error message if login fails.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @param model The model to hold attributes for the view.
     * @return The view name to display.
     */
    @PostMapping("/login")
    public String handleLogin(String username, String password, Model model) {
        Optional<User> user = userService.findByUsername(username);

        // Validate user credentials
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String role = user.get().getRole();
            if ("ADMIN".equalsIgnoreCase(role)) {
                return "redirect:/materials"; // Redirect admins
            } else if ("USER".equalsIgnoreCase(role)) {
                return "redirect:/materials-user"; // Redirect users
            }
        }

        model.addAttribute("error", "Invalid username or password");
        return "index"; // Stay on the login page if login fails
    }

}
