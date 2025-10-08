package com.novavivienda.backend.dtos.general;

import lombok.Builder;

@Builder
public record ResponseMessageDto(
        String message
) {
}
