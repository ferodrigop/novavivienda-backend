package com.novavivienda.backend.controllers.auth;

import com.novavivienda.backend.dtos.auth.*;
import com.novavivienda.backend.dtos.general.ResponseMessageDto;
import com.novavivienda.backend.entities.user.RoleName;
import com.novavivienda.backend.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Value("${app.security.auth.secret-key}")
    private String authSecretKey;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessageDto> signUp(
            @RequestParam(defaultValue = "ROLE_CLIENT") RoleName role,
            @RequestParam(required = false) String secretKey,
            @RequestBody @Validated SignUpRequestDto signUpRequestDto
    ) {
        if (role.equals(RoleName.ROLE_SYSTEM_ADMIN)) {
            if (Objects.isNull(secretKey) || !authSecretKey.equals(secretKey)) {
                throw new AccessDeniedException("You are not allowed to access this resource");
            }
        }
        authenticationService.signUp(signUpRequestDto, role);
        return ResponseEntity.ok().body(
                ResponseMessageDto.builder()
                        .message("A Verification Email has been sent, check your inbox")
                        .build()
        );
    }

    @PostMapping("/email/resend-verification")
    public ResponseEntity<Void> resendEmailVerification(
            @RequestParam String email
    ) {
        authenticationService.resendEmailVerification(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Void> verifyEmail(
            @RequestParam("email") String email,
            @RequestParam("token") String token
    ) {
        UUID newUserId = authenticationService.verifyEmail(email, token);
        URI location = URI.create(String.format("/api/v1/users/%s", newUserId));
        return ResponseEntity.ok().location(location).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Validated SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = authenticationService.signIn(signInRequestDto);
        return ResponseEntity.ok(signInResponseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SignInResponseDto> refreshToken(@RequestBody @Validated RefreshTokenRequestDto refreshTokenRequestDto) {
        SignInResponseDto signInResponseDto = authenticationService.refreshToken(
                refreshTokenRequestDto.refreshToken()
        );
        return ResponseEntity.ok(signInResponseDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(@RequestBody @Validated SignOutDto signOutDto) {
        authenticationService.signOut(signOutDto.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseMessageDto> forgotPassword(@RequestBody @Validated ForgotPasswordDto forgotPasswordDto) {
        authenticationService.initiatePasswordReset(forgotPasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("If the email is associated with an account, a reset link has been sent")
                        .build()
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseMessageDto> resetPassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        authenticationService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("Password reset successfully. You may now log in")
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ResponseEntity<ResponseMessageDto> changePassword(@RequestBody @Validated ChangePasswordDto changePasswordDto) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        authenticationService.changePassword(changePasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("Password successfully changed")
                        .build()
        );
    }
}
