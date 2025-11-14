package com.freelancenexus.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway Swagger Configuration
 * 
 * Aggregates OpenAPI documentation from all microservices
 * into a single Swagger UI interface accessible through the API Gateway.
 * 
 * Users can select which service API to view from a dropdown in Swagger UI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configure API groups for each microservice
     * 
     * Each group represents a separate microservice's API documentation.
     * The gateway will fetch the OpenAPI spec from each service and display
     * them in a unified Swagger UI.
     * 
     * @param locator RouteDefinitionLocator for dynamic route discovery
     * @return List of GroupedOpenApi configurations
     */
    @Bean
    @Primary
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        
        // User Service API
        groups.add(GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/api/users/**")
                .build());
        
        // Freelancer Service API
        groups.add(GroupedOpenApi.builder()
                .group("freelancer-service")
                .pathsToMatch("/api/freelancers/**")
                .build());
        
        // Project Service API  
        groups.add(GroupedOpenApi.builder()
                .group("project-service")
                .pathsToMatch("/api/projects/**", "/api/proposals/**", "/api/ai/**")
                .build());
        
        // Payment Service API
        groups.add(GroupedOpenApi.builder()
                .group("payment-service")
                .pathsToMatch("/api/payments/**")
                .build());
        
        // Notification Service API
        groups.add(GroupedOpenApi.builder()
                .group("notification-service")
                .pathsToMatch("/api/notifications/**")
                .build());
        
        return groups;
    }
}