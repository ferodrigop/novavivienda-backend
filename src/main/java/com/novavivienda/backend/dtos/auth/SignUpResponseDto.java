package com.novavivienda.backend.dtos.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SignUpResponseDto(
        UUID userId
) {
}
