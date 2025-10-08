package com.novavivienda.backend.configurations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.sendgrid")
public record SendGridConfigurationProperties(
        @NotBlank
        String apiKey,

        @NotBlank
        String fromEmail,

        @NotBlank
        String fromName
) {
}
