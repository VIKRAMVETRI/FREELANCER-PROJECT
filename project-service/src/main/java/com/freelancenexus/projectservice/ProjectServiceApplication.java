package com.freelancenexus.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for the Project Service application.
 * This is the entry point for the Spring Boot application.
 * It enables service discovery using Spring Cloud.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProjectServiceApplication {

    /**
     * The main method to start the Project Service application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }
}