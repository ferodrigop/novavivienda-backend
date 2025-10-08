package com.novavivienda.backend.exceptions;

public class NewPasswordMismatchException extends RuntimeException {
    public NewPasswordMismatchException(String message) {
        super(message);
    }
}
