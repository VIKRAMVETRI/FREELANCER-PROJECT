package com.freelancenexus.userservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${codespace.name:localhost}")
    private String codespaceName;

    @Value("${codespace.domain:local}")
    private String codespaceDomain;

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI userServiceOpenAPI() {
        // Security scheme for JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter JWT token obtained from /api/users/login");

        // Security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        // Build server URLs dynamically
        List<Server> servers = new ArrayList<>();

        // Check if we're in Codespaces
        if (!"localhost".equals(codespaceName) && !"local".equals(codespaceDomain)) {
            // Codespaces URLs
            servers.add(new Server()
                    .url("https://" + codespaceName + "-" + serverPort + "." + codespaceDomain)
                    .description("User Service - Direct Access (Codespaces)"));
            
            servers.add(new Server()
                    .url("https://" + codespaceName + "-8765." + codespaceDomain)
                    .description("User Service - Via Gateway (Codespaces)"));
        }

        // Local URLs (always available)
        servers.add(new Server()
                .url("http://localhost:" + serverPort)
                .description("User Service - Direct Access (Local)"));
        
        servers.add(new Server()
                .url("http://localhost:8765")
                .description("User Service - Via Gateway (Local)"));

        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("FreelanceNexus User Service - Handles user registration, authentication, and profile management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FreelanceNexus Team")
                                .email("support@freelancenexus.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(servers)
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}