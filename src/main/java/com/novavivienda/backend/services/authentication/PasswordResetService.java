package com.novavivienda.backend.services.authentication;

import com.novavivienda.backend.entities.password_reset.PasswordReset;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.NotFoundException;
import com.novavivienda.backend.repositories.password_reset.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;

    @Value("${app.password.reset.ttl}")
    private long secretKey;

    @Transactional
    public PasswordReset createPasswordReset(User user) {
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordReset.setExpiresAt(Instant.now().plusSeconds(secretKey));
        return passwordResetRepository.saveAndFlush(passwordReset);
    }

    public PasswordReset findPasswordResetByIdAndExpiresAtAfter(UUID id, Instant date) {
        return passwordResetRepository.findByIdAndExpiresAtAfter(id, date)
                .orElseThrow(() -> new NotFoundException("Invalid or expired reset token"));
    }
}
