package com.freelancenexus.projectservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * <p>Security configuration for the Project Service. Configures HTTP security rules
 * for stateless API access, disables CSRF protection, and defines permit-all endpoints
 * for actuator health checks, Swagger documentation, and all API endpoints.</p>
 *
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define the main SecurityFilterChain bean for HTTP security.
     *
     * <p>Configured rules:
     * <ul>
     *   <li>CSRF is disabled (stateless API design)</li>
     *   <li>Actuator, Swagger, and all API endpoints are permitted without authentication</li>
     *   <li>All other requests require authentication</li>
     *   <li>Session management is stateless</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} to configure
     * @return a built {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs while building the security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/api/projects/**",
                                "/api/proposals/**",
                                "/api/ai/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}