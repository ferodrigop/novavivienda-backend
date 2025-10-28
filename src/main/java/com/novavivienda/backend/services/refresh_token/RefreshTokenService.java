package com.novavivienda.backend.services.refresh_token;

import com.novavivienda.backend.entities.refresh_token.RefreshTokenData;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refresh_token:";

    @Value("${app.security.jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationMs;

    public RefreshTokenData createNewRefreshToken(User user) {
        UUID tokenId = UUID.randomUUID();
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(refreshTokenExpirationMs);
        
        RefreshTokenData refreshTokenData = RefreshTokenData.builder()
                .id(tokenId)
                .userId(user.getId())
                .expiresAt(expiresAt)
                .createdAt(now)
                .build();
        
        String key = REFRESH_TOKEN_KEY_PREFIX + tokenId;
        redisTemplate.opsForValue().set(key, refreshTokenData, refreshTokenExpirationMs, TimeUnit.MILLISECONDS);
        
        return refreshTokenData;
    }

    public RefreshTokenData findRefreshTokenByIdAndExpiresAtAfter(UUID refreshTokenId, Instant date) {
        String key = REFRESH_TOKEN_KEY_PREFIX + refreshTokenId;
        Object value = redisTemplate.opsForValue().get(key);
        
        if (value == null) {
            throw new NotFoundException("Invalid or expired refresh token id");
        }
        
        RefreshTokenData refreshTokenData = (RefreshTokenData) value;
        
        if (refreshTokenData.getExpiresAt().isBefore(date)) {
            // Token has expired, delete it
            redisTemplate.delete(key);
            throw new NotFoundException("Invalid or expired refresh token id");
        }
        
        return refreshTokenData;
    }

    public void deleteRefreshTokenById(UUID id) {
        String key = REFRESH_TOKEN_KEY_PREFIX + id;
        redisTemplate.delete(key);
    }
}
