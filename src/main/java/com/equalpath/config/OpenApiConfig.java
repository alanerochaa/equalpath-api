package com.equalpath.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EqualPath API",
                version = "1.0",
                description = "API de recomendação de trilhas e skills do projeto EqualPath",
                contact = @Contact(
                        name = "EqualPath Squad",
                        email = "contato@equalpath.com"
                )
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI equalpathOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("EqualPath API")
                        .version("1.0")
                        .description("API para recomendação de trilhas de carreira com base em skills do usuário.")
                        .license(new License().name("FIAP - GS 2025/2")));
    }

    @Bean
    public GroupedOpenApi equalpathGroup() {
        return GroupedOpenApi.builder()
                .group("equalpath")
                .pathsToMatch("/api/**")
                .build();
    }
}
