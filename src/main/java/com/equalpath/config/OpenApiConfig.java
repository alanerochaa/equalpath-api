package com.equalpath.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EqualPath API",
                version = "1.0",
                description = "API de recomendação de trilhas de carreira com base em skills do usuário.",
                contact = @Contact(
                        name = "EqualPath Squad",
                        email = "contato@equalpath.com"
                )
        ),
        servers = {
                // Usa o mesmo host de onde o Swagger está sendo servido (local ou Render)
                @Server(url = "/", description = "Ambiente atual (local / Render)")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI equalpathOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name("bearerAuth")
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("EqualPath API")
                        .version("1.0")
                        .description("API para recomendação de trilhas de carreira com base em skills do usuário.")
                        .license(new License().name("FIAP - GS 2025/2")));
    }
}
