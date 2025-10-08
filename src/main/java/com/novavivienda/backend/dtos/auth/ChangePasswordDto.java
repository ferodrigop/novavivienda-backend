package com.novavivienda.backend.dtos.auth;

import com.novavivienda.backend.configurations.custom_annotations.NotNullOrBlank;
import com.novavivienda.backend.utils.CredentialsPatternsValidator;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ChangePasswordDto(
        @NotNullOrBlank(message = "Provide the Current Password")
        String currentPassword,

        @Pattern(regexp = CredentialsPatternsValidator.PASSWORD_REGEXP, message = CredentialsPatternsValidator.PASSWORD_ERROR_MESSAGE)
        @NotNullOrBlank(message = "Provide the New Password")
        String newPassword,

        @Pattern(regexp = CredentialsPatternsValidator.PASSWORD_REGEXP, message = CredentialsPatternsValidator.PASSWORD_ERROR_MESSAGE)
        @NotNullOrBlank(message = "Provide the Confirmation for the New Password")
        String newPasswordConfirmation
) {
}
