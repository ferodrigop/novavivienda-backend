package com.novavivienda.backend.exceptions;

import com.novavivienda.backend.dtos.exception.ErrorResponseDto;
import com.novavivienda.backend.dtos.exception.ValidationErrorDto;
import com.novavivienda.backend.utils.ExceptionUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Invalid credentials",
                "The provided email or password is incorrect",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Invalid credentials",
                "The provided email or password is incorrect",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "We were not able to find the requested resource",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDenied(AuthorizationDeniedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Forbidden",
                "Access denied. You do not have the necessary permissions to perform this action",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponseDto> tooManyRequestsException(TooManyRequestsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.TOO_MANY_REQUESTS;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Too Many Requests",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> emailVerificationExceptionException(AuthenticationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Forbidden",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredJwt(ExpiredJwtException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Invalid Authentication",
                "The provided JWT is incorrect or expired",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleMalformedJwt(MalformedJwtException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Invalid Authentication",
                "The provided JWT is incorrect or expired",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleUnsupportedJwt(UnsupportedJwtException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Invalid Authentication",
                "The provided JWT is incorrect or expired",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDto> handleSignature(SignatureException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "There was a problem with the signature",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDenied(AccessDeniedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Forbidden",
                "Access denied. You do not have the necessary permissions to perform this action",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Forbidden",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ValidationErrorDto> validationErrors = ExceptionUtils.getConstraintViolationExceptionValidationErrors(ex);
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Validation Error",
                "Some fields contain invalid values",
                request.getDescription(false).replace("uri=", ""),
                ex,
                validationErrors
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ValidationErrorDto> validationErrors = ExceptionUtils.getMethodArgumentNotValidExceptionValidationErrors(ex);
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Validation Error",
                "Some fields contain invalid values",
                request.getDescription(false).replace("uri=", ""),
                ex,
                validationErrors
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Data Integrity Violation",
                ExceptionUtils.getDataIntegrityViolationExceptionMessage(ex.getMostSpecificCause().getMessage()),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "We were not able to find the requested resource",
                "Try again",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyInUse(EmailAlreadyInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Unable to proceed",
                "An account with this email address already exists",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ExceptionUtils.extractDetailedMessage(ex);
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Malformed JSON Request",
                message,
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(NewPasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleNewPasswordMismatch(NewPasswordMismatchException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Unable to proceed",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(CurrentPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleCurrentPassword(CurrentPasswordException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                "Unable to proceed with password change",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }
}
