package com.novavivienda.backend.configurations.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.security.jwt")
public class SecurityProperties {
    @NotNull(message = "JWT secret key must not be null")
    private String secretKey;

    @NotNull(message = "Access token expiration time must not be null")
    private Long accessTokenExpirationTime;

    @NotNull(message = "Refresh token expiration time must not be null")
    private Long refreshTokenExpirationTime;
}
