package com.freelancenexus.payment.config;

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

    @Value("${server.port:8084}")
    private String serverPort;

    @Bean
    public OpenAPI paymentServiceOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter JWT token obtained from /api/users/login");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        // Build server URLs dynamically
        List<Server> servers = new ArrayList<>();

        // Check if we're in Codespaces
        if (!"localhost".equals(codespaceName) && !"local".equals(codespaceDomain)) {
            // Codespaces URLs
            servers.add(new Server()
                    .url("https://" + codespaceName + "-" + serverPort + "." + codespaceDomain)
                    .description("Payment Service - Direct Access (Codespaces)"));
            
            servers.add(new Server()
                    .url("https://" + codespaceName + "-8765." + codespaceDomain + "/api/payments")
                    .description("Payment Service - Via Gateway (Codespaces)"));
        }

        // Local URLs (always available)
        servers.add(new Server()
                .url("http://localhost:" + serverPort)
                .description("Payment Service - Direct Access (Local)"));
        
        servers.add(new Server()
                .url("http://localhost:8765/api/payments")
                .description("Payment Service - Via Gateway (Local)"));

        return new OpenAPI()
                .info(new Info()
                        .title("Payment Service API")
                        .description("FreelanceNexus Payment Service - Handles UPI payments, escrow management, transaction processing, and refunds")
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