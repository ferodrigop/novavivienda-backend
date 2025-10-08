package com.novavivienda.backend.utils;

public class CredentialsPatternsValidator {
    public static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,19}$";
    public static final String PASSWORD_ERROR_MESSAGE = "Invalid password entered! Password must consist of at least one small letter, " +
            "one capital letter, one digit, one special symbol and must be between 8 - 20 characters long (inclusive).";
    public static final String EMAIL_REGEXP = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String EMAIL_ERROR_MESSAGE = "Invalid email address entered! Email address should follow RFC-5322 standard!";
}
