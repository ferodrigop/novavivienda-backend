package com.novavivienda.backend.configurations.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {
    @NotNull(message = "Datasource username must not be null")
    private String username;

    @NotNull(message = "Datasource password must not be null")
    private String password;

    @NotNull(message = "Datasource URL must not be null")
    private String url;
}