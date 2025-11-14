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

import java.util.List;

@Configuration
@Operation(security = @SecurityRequirement(name = "Bearer Authentication"))
public class OpenApiConfig {

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
                .servers(List.of(
                        // Direct service access (Codespaces)
                        new Server()
                                .url("https://${CODESPACE_NAME}-8081.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}")
                                .description("User Service - Direct Access (Codespaces)"),
                        // Gateway access (Codespaces)
                        new Server()
                                .url("https://${CODESPACE_NAME}-8765.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}/api/users")
                                .description("User Service - Via Gateway (Codespaces)"),
                        // Local development
                        new Server()
                                .url("http://localhost:8081")
                                .description("User Service - Direct Access (Local)"),
                        new Server()
                                .url("http://localhost:8765/api/users")
                                .description("User Service - Via Gateway (Local)")
                ))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}