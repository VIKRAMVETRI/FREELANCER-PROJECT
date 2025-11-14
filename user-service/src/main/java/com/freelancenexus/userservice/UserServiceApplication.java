package com.freelancenexus.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * UserServiceApplication
 *
 * <p>Main entry point for the Freelance Nexus User Service microservice.
 * This Spring Boot application provides user management, authentication, and
 * profile management capabilities for the Freelance Nexus platform.</p>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @SpringBootApplication} — enables auto-configuration and component scanning</li>
 *   <li>{@code @EnableDiscoveryClient} — registers this service with Eureka service registry</li>
 * </ul>
 * </p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	/**
	 * Application entry point.
	 *
	 * <p>Starts the Spring Boot application context for the User Service.
	 * Command-line arguments can be passed to configure runtime behavior
	 * (profiles, properties, etc.).</p>
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
