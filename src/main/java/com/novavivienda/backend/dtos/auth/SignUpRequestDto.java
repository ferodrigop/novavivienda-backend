package com.novavivienda.backend.dtos.auth;

import com.novavivienda.backend.configurations.custom_annotations.NotNullOrBlank;
import com.novavivienda.backend.utils.CredentialsPatternsValidator;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignUpRequestDto(
        @NotNullOrBlank(message = "Provide your First Name")
        String firstName,

        @NotNullOrBlank(message = "Provide your Last Name")
        String lastName,

        @Pattern(regexp = CredentialsPatternsValidator.EMAIL_REGEXP, message = "Invalid email address entered!")
        @NotNullOrBlank(message = "Provide your email address")
        String email,

        @Pattern(regexp = CredentialsPatternsValidator.PASSWORD_REGEXP, message = CredentialsPatternsValidator.PASSWORD_ERROR_MESSAGE)
        @NotNullOrBlank(message = "Provide your New Password")
        String password
) {
}
