/** Class for configuring application security. */
package com.example.construction_materials.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * - Disables CSRF for simplicity in development; consider enabling it in production.
     * - Sets up role-based access control for endpoints:
     *      - Public access: Login, Signup, Password Reset, and CSS resources.
     *      - Admin-only access: Material management endpoints.
     *      - User-only access: Material browsing and shopping cart endpoints.
     * - Configures form-based login with role-based redirection.
     * - Enables logout functionality.
     *
     * @param http The HttpSecurity object for configuring security rules.
     * @return Configured SecurityFilterChain object.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disables CSRF (Cross-Site Request Forgery) protection.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/password-reset/**", "/CSS/**").permitAll() // Publicly accessible endpoints.
                        .requestMatchers("/materials/**", "/materials-delete/**").hasRole("ADMIN") // Restricted to Admin role.
                        .requestMatchers("/materials-user/**", "/materials-user/cart/**").hasRole("USER") // Restricted to User role.
                        .anyRequest().authenticated() // All other endpoints require authentication.
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page.
                        .permitAll() // Allows all users to access the login page.
                        .successHandler((request, response, authentication) -> {
                            // Handles post-login redirection based on user role.
                            String role = authentication.getAuthorities().stream()
                                    .findFirst()
                                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                                    .orElse("");

                            if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/materials"); // Redirect Admins to materials page.
                            } else if ("ROLE_USER".equals(role)) {
                                response.sendRedirect("/materials-user"); // Redirect Users to user materials page.
                            } else {
                                response.sendRedirect("/login?error"); // Redirect for invalid roles.
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Custom logout URL.
                        .logoutSuccessUrl("/login?logout") // Redirect to login page after logout.
                        .permitAll() // Allows all users to access the logout functionality.
                );

        return http.build(); // Builds and returns the SecurityFilterChain object.
    }

    /**
     * Configures the password encoder bean.
     *
     * Uses BCryptPasswordEncoder for secure password hashing.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager bean.
     *
     * Provides an AuthenticationManager for managing authentication.
     *
     * @param authConfig The AuthenticationConfiguration object.
     * @return Configured AuthenticationManager instance.
     * @throws Exception If an error occurs during setup.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
