package com.novavivienda.backend.services.authentication;

import com.novavivienda.backend.dtos.auth.*;
import com.novavivienda.backend.entities.refresh_token.RefreshTokenData;
import com.novavivienda.backend.entities.user.RoleName;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.EmailAlreadyInUseException;
import com.novavivienda.backend.exceptions.EmailVerificationException;
import com.novavivienda.backend.repositories.user.UserRepository;
import com.novavivienda.backend.services.jwt.JwtService;
import com.novavivienda.backend.services.refresh_token.RefreshTokenService;
import com.novavivienda.backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordService passwordService;
    private final EmailVerificationService emailVerificationService;
    private final UserRepository userRepository;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto, RoleName roleName) {
        userRepository.findByEmail(signUpRequestDto.email())
                .ifPresentOrElse(user -> {
                    if (!user.isEmailVerified()) {
                        emailVerificationService.sendVerificationToken(user.getEmail());
                        throw new EmailVerificationException("Pending Email verification. Check your email for the verification code");
                    } else {
                        throw new EmailAlreadyInUseException();
                    }
                },
                () -> {
                    User newUser = userService.createNewUser(signUpRequestDto, roleName);
                    emailVerificationService.sendVerificationToken(newUser.getEmail());
                });
    }

    public void resendEmailVerification(String email) {
        emailVerificationService.resendVerificationToken(email);
    }

    public UUID verifyEmail(String email, String token) {
        return emailVerificationService.verifyEmail(email, token).getId();
    }

    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.email(),
                        signInRequestDto.password()
                )
        );
        String accessToken = jwtService.generateAccessToken(signInRequestDto.email());
        User user = (User) authentication.getPrincipal();
        RefreshTokenData refreshTokenData = refreshTokenService.createNewRefreshToken(user);
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenData.getId())
                .build();
    }

    @Transactional
    public SignInResponseDto refreshToken(UUID refreshTokenId) {
        RefreshTokenData refreshTokenData = refreshTokenService.findRefreshTokenByIdAndExpiresAtAfter(
                refreshTokenId, Instant.now()
        );
        User user = userService.findUserById(refreshTokenData.getUserId());
        final String newAccessToken = jwtService.generateAccessToken(user.getEmail());
        return SignInResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenData.getId())
                .build();
    }

    @Transactional
    public void signOut(UUID refreshTokenId) {
        refreshTokenService.deleteRefreshTokenById(refreshTokenId);
    }

    public void initiatePasswordReset(ForgotPasswordDto forgotPasswordDto) {
        passwordService.initiatePasswordReset(forgotPasswordDto);
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        passwordService.resetPassword(resetPasswordDto);
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        passwordService.changePassword(changePasswordDto);
    }
}
