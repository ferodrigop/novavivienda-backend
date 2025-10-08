package com.novavivienda.backend.exceptions;

public class CurrentPasswordException extends RuntimeException {
    public CurrentPasswordException(String message) {
        super(message);
    }
}
