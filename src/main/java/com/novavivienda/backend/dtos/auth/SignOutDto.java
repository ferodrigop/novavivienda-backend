package com.novavivienda.backend.dtos.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SignOutDto(
        @NotNull(message = "Refresh Token cannot be empty")
        UUID refreshToken
) {
}
