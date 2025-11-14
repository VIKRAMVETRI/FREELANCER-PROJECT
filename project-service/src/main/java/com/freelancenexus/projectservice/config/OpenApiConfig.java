package com.freelancenexus.projectservice.config;

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
/**
 * Configuration class for setting up OpenAPI (Swagger) documentation for the Project Service.
 * <p>
 * This class defines the OpenAPI specification, including API metadata, server URLs,
 * security schemes, and other components required for API documentation.
 * </p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Configures API title, description, version, contact, and license information.</li>
 *   <li>Defines multiple server URLs for local and Codespaces environments.</li>
 *   <li>Sets up JWT Bearer Authentication for securing API endpoints.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * This configuration is automatically loaded by Spring Boot during application startup.
 *
 * @author FreelanceNexus Team
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * The server port for the Project Service.
     * Defaults to {@code 8088} if not specified in application properties.
     */
    @Value("${server.port:8088}")
    private String serverPort;

    /**
     * Creates and configures the OpenAPI specification for the Project Service.
     *
     * @return an {@link OpenAPI} instance containing API metadata, servers, security schemes, and requirements.
     *
     * <p><b>Security:</b></p>
     * Adds a Bearer Authentication scheme using JWT tokens.
     *
     * <p><b>Servers:</b></p>
     * Includes URLs for:
     * <ul>
     *   <li>Direct access via Codespaces</li>
     *   <li>Access through API Gateway in Codespaces</li>
     *   <li>Local direct access</li>
     *   <li>Local access via API Gateway</li>
     * </ul>
     */
    @Bean
    public OpenAPI projectServiceOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter JWT token obtained from /api/users/login");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info()
                        .title("Project Service API")
                        .description("FreelanceNexus Project Service - Manages projects, proposals, AI recommendations, and freelancer-client matching")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FreelanceNexus Team")
                                .email("support@freelancenexus.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("https://${CODESPACE_NAME}-8088.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}")
                                .description("Project Service - Direct Access (Codespaces)"),
                        new Server()
                                .url("https://${CODESPACE_NAME}-8765.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}/api/projects")
                                .description("Project Service - Via Gateway (Codespaces)"),
                        new Server()
                                .url("http://localhost:8088")
                                .description("Project Service - Direct Access (Local)"),
                        new Server()
                                .url("http://localhost:8765/api/projects")
                                .description("Project Service - Via Gateway (Local)")
                ))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}