package ru.example.recommendationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI discountsOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Recommendation API")
                .version("1.0.0")
                .description("Система рекомендаций")
        );
    }
}
