package com.novavivienda.backend.dtos.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
