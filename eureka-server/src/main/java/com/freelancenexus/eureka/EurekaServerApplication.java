package com.freelancenexus.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EurekaServerApplication
 *
 * <p>Main entry point for the Freelance Nexus Service Discovery server.
 * This Spring Boot application is annotated with {@code @EnableEurekaServer}
 * to provide a Eureka service registry where microservices can register
 * themselves for dynamic discovery, health monitoring, and load balancing.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Centralized service registry</li>
 *   <li>Real-time service instance tracking</li>
 *   <li>Heartbeat-based health checks</li>
 *   <li>Self-preservation mode during network partitions</li>
 *   <li>Web dashboard for monitoring services</li>
 * </ul>
 * </p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	/**
	 * Application entry point.
	 *
	 * <p>Starts the Spring Boot application context for the Eureka Server.
	 * Runtime arguments (profiles, server.port overrides, etc.) can be passed
	 * via {@code args}.</p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Eureka Server started successfully!");
        System.out.println("Dashboard: http://localhost:8761");
        System.out.println("==============================================");
	}

}