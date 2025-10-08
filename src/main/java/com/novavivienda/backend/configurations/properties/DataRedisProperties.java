package com.novavivienda.backend.configurations.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.data.redis")
public class DataRedisProperties {
    @NotNull(message = "Data Redis host must not be null")
    private String host;

    @NotNull(message = "Datasource URL must not be null")
    private String port;

    @NotNull(message = "Data Redis password must not be null")
    private String password;
}
