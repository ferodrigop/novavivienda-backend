package com.novavivienda.backend.utils;

import com.novavivienda.backend.dtos.exception.ErrorResponseDto;
import com.novavivienda.backend.dtos.exception.ValidationErrorDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionUtils {
    public static ErrorResponseDto createErrorResponseDto(
            HttpStatus status,
            String error,
            String message,
            String path
    ) {
        return ErrorResponseDto.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponseDto createErrorResponseDto(
            HttpStatus status,
            String error,
            String message,
            String path,
            Exception ex
    ) {
        ex.printStackTrace();
        return createErrorResponseDto(status, error, message, path);
    }

    public static ErrorResponseDto createErrorResponseDto(
            HttpStatus status,
            String error,
            String message,
            String path,
            Exception ex,
            List<ValidationErrorDto> validationErrors
    ) {
        ex.printStackTrace();
        return ErrorResponseDto.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();
    }

    private static final Map<String, String> dataIntegrityViolationMap = new HashMap<>();

    static {
        dataIntegrityViolationMap.put("unique constraint", "A record with this value already exists for {field}");
        dataIntegrityViolationMap.put("foreign key constraint", "The referenced record does not exist for {field}");
        dataIntegrityViolationMap.put("not-null constraint", "{field} is required and cannot be null");
        dataIntegrityViolationMap.put("check constraint", "The provided data does not meet the required criteria for {field}");
        dataIntegrityViolationMap.put("primary key constraint", "A record with this ID already exists for {field}");
        dataIntegrityViolationMap.put("exclusion constraint", "The provided value conflicts with existing values for {field}");
        dataIntegrityViolationMap.put("domain constraint", "The provided value does not match the required format or domain for {field}");
    }

    public static String getDataIntegrityViolationExceptionMessage(String exceptionMessage) {
        String field = extractField(exceptionMessage);

        return dataIntegrityViolationMap.entrySet().stream()
                .filter(entry -> exceptionMessage.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("A data integrity issue occurred for {field}")
                .replace("{field}", field);
    }

    public static List<ValidationErrorDto> getMethodArgumentNotValidExceptionValidationErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(error -> new ValidationErrorDto(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()))
                .toList();
    }

    public static List<ValidationErrorDto> getConstraintViolationExceptionValidationErrors(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(error -> new ValidationErrorDto(
                        error.getPropertyPath().toString(),
                        error.getMessage()))
                .toList();
    }

    public static String extractField(String message) {
        int startIndex = message.indexOf("Key (") + 5;
        int endIndex = message.indexOf(")", startIndex);
        if (startIndex > 4 && endIndex > startIndex) {
            return message.substring(startIndex, endIndex);
        } else if (message.contains("column ")) {
            return message.split("column ")[1].split(" ")[0];
        } else {
            return "unknown field";
        }
    }

    public static String extractDetailedMessage(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof com.fasterxml.jackson.databind.JsonMappingException) {
            com.fasterxml.jackson.databind.JsonMappingException jsonMappingException =
                    (com.fasterxml.jackson.databind.JsonMappingException) ex.getCause();
            return "Invalid JSON: " + jsonMappingException.getOriginalMessage();
        }

        if (ex.getCause() instanceof com.fasterxml.jackson.core.JsonParseException) {
            com.fasterxml.jackson.core.JsonParseException jsonParseException =
                    (com.fasterxml.jackson.core.JsonParseException) ex.getCause();
            return "Invalid JSON format: " + jsonParseException.getOriginalMessage();
        }

        // Fallback message
        return "Malformed JSON or request body could not be read.";
    }
}
