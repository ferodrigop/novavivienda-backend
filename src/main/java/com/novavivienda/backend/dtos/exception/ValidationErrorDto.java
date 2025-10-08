package com.novavivienda.backend.dtos.exception;

public record ValidationErrorDto(
        String field,  // The name of the field with the error
        String message // The error message
) {
}
