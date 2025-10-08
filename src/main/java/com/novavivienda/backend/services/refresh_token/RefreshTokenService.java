package com.novavivienda.backend.services.refresh_token;

import com.novavivienda.backend.entities.refresh_token.RefreshToken;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.NotFoundException;
import com.novavivienda.backend.repositories.refresh_token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.security.jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationMs;

    @Transactional
    public RefreshToken createNewRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenExpirationMs));
        return refreshTokenRepository.saveAndFlush(refreshToken);
    }

    public RefreshToken findRefreshTokenByIdAndExpiresAtAfter(UUID refreshTokenId, Instant date) {
        return refreshTokenRepository.findByIdAndExpiresAtAfter(refreshTokenId, date)
                .orElseThrow(() -> new NotFoundException("Invalid or expired refresh token id"));
    }

    @Transactional
    public void deleteRefreshTokenById(UUID id) {
        refreshTokenRepository.deleteById(id);
    }
}
