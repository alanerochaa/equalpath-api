package com.equalpath.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI equalPathOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EqualPath API")
                        .version("v1")
                        .description("API da plataforma EqualPath - Orientação de carreira baseada em skills"));
    }
}
