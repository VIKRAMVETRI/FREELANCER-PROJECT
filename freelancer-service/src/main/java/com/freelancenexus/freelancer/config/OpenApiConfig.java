package com.freelancenexus.freelancer.config;

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
public class OpenApiConfig {

    @Value("${server.port:8082}")
    private String serverPort;

    @Bean
    public OpenAPI freelancerServiceOpenAPI() {
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
                        .title("Freelancer Service API")
                        .description("FreelanceNexus Freelancer Service - Manages freelancer profiles, portfolios, ratings, and skill management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FreelanceNexus Team")
                                .email("support@freelancenexus.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("https://${CODESPACE_NAME}-8082.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}")
                                .description("Freelancer Service - Direct Access (Codespaces)"),
                        new Server()
                                .url("https://${CODESPACE_NAME}-8765.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}/api/freelancers")
                                .description("Freelancer Service - Via Gateway (Codespaces)"),
                        new Server()
                                .url("http://localhost:8082")
                                .description("Freelancer Service - Direct Access (Local)"),
                        new Server()
                                .url("http://localhost:8765/api/freelancers")
                                .description("Freelancer Service - Via Gateway (Local)")
                ))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}