package com.novavivienda.backend.services.authentication;

import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.EmailVerificationException;
import com.novavivienda.backend.exceptions.NotFoundException;
import com.novavivienda.backend.repositories.user.UserRepository;
import com.novavivienda.backend.services.email.EmailDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final EmailDispatcher emailDispatcher;

    public void sendVerificationToken(String email) {
        final var token = otpService.generateAndStoreOtp(email);
        try {
            emailDispatcher.dispatchEmailVerification(email, token);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email)
                .filter(u -> !u.isEmailVerified())
                .orElseThrow(() -> new NotFoundException("Email not found or already verified"));
        sendVerificationToken(user.getEmail());
    }

    @Transactional
    public User verifyEmail(String email, String token) {
        if (!otpService.isOtpValid(email, token)) {
            throw new EmailVerificationException("Token invalid or expired");
        }
        otpService.deleteOtp(email);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EmailVerificationException("User account has been deleted or deactivated"));
        if (user.isEmailVerified()) {
            throw new EmailVerificationException("Email is already verified");
        }
        user.setEmailVerified(true);
        return user;
    }
}
