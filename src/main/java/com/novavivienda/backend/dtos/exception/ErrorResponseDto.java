package com.novavivienda.backend.dtos.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public record ErrorResponseDto(
        int status,             // HTTP status code
        String error,           // Short error description
        String message,         // Detailed error message for context
        String path,            // The request path that caused the exception
        LocalDateTime timestamp, // Timestamp when the error occurred
        List<ValidationErrorDto> validationErrors // List of field-specific validation errors
) {
}
