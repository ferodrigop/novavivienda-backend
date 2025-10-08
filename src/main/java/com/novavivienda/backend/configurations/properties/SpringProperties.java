package com.novavivienda.backend.configurations.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.application")
public class SpringProperties {
    @NotNull(message = "Base URL must not be null")
    private String baseUrl;
}