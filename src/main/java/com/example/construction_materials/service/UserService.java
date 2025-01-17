/** Service class for managing user-related operations. */
package com.example.construction_materials.service;

import com.example.construction_materials.model.User;
import com.example.construction_materials.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for injecting dependencies.
     *
     * @param userRepository Repository for managing user data.
     * @param passwordEncoder Encoder for secure password handling.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Loads a user by their username for authentication purposes.
     *
     * @param username The username of the user to load.
     * @return UserDetails for Spring Security.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    /**
     * Saves a new user to the database after encoding their password.
     *
     * @param user The user to save.
     */
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Updates the password for an existing user.
     *
     * @param username The username of the user.
     * @param newPassword The new password to set.
     */
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Creates a new user with validation for username, email, and password.
     *
     * @param username The username of the new user.
     * @param email The email address of the new user.
     * @param password The password for the new user.
     * @param role The role of the new user (e.g., "USER", "ADMIN").
     */
    public void createUser(String username, String email, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format. Must include a valid domain (e.g., .com, .ro).");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password. Must contain at least 8 characters, including an uppercase letter, a lowercase letter, a number, and a special character.");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);
    }

    /**
     * Validates the password format based on complexity requirements.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    /**
     * Validates the email format based on a standard pattern.
     *
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user, if found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
