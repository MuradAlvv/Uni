package com.example.unitech.configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenAPI30Configuration {
    public static final String BEARER_AUTHENTICATION = "Bearer Authentication";
}
