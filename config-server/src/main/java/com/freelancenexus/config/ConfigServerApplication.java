package com.freelancenexus.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Profile;

/**
 * ConfigServerApplication
 *
 * <p>Main entry point for the Freelance Nexus centralized configuration server.
 * This Spring Boot application is annotated with {@code @EnableConfigServer} to
 * provide Spring Cloud Config Server functionality. It serves configuration
 * properties to microservices from a centralized location (e.g. filesystem or Git)
 * and supports environment-specific profiles and dynamic refresh.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Centralized configuration management</li>
 *   <li>Environment-specific configurations (dev, staging, prod)</li>
 *   <li>Dynamic configuration refresh without service restart</li>
 *   <li>Version control integration support</li>
 * </ul>
 * </p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	/**
	 * Application entry point.
	 *
	 * <p>Starts the Spring Boot application context for the Config Server.
	 * Typical runtime arguments (profiles, server.port overrides, etc.) can
	 * be provided via {@code args}.</p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
		System.out.println("==============================================");
        System.out.println("Config Server started successfully on port 8888");
        System.out.println("==============================================");
	}

}
