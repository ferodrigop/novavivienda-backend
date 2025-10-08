package com.novavivienda.backend.dtos.auth;

import com.novavivienda.backend.configurations.custom_annotations.NotNullOrBlank;
import com.novavivienda.backend.utils.CredentialsPatternsValidator;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ForgotPasswordDto(
        @Pattern(regexp = CredentialsPatternsValidator.EMAIL_REGEXP, message = "Invalid email address entered!")
        @NotNullOrBlank(message = "Provide the email address")
        String email
) {
}
