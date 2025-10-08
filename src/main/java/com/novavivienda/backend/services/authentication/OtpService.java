package com.novavivienda.backend.services.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OtpService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final RedisTemplate<String, String> redisTemplate;

    public String generateAndStoreOtp(final String email) {
        final var otp = generateOtp("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789", 6);
        final var cacheKey = getCacheKey(email);

        redisTemplate.opsForValue().set(
                cacheKey, otp, Duration.ofMinutes(5));

        return otp;
    }

    public boolean isOtpValid(final String email, final String otp) {
        final var cacheKey = getCacheKey(email);
        return Objects.equals(
                redisTemplate.opsForValue().get(cacheKey), otp);
    }

    public void deleteOtp(final String email) {
        final var cacheKey = getCacheKey(email);
        redisTemplate.delete(cacheKey);
    }

    private String getCacheKey(String email) {
        return "otp:%s".formatted(email);
    }

    private String generateOtp(String characters, Integer length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(characters.length());
            otp.append(characters.charAt(index));
        }
        return otp.toString();
    }
}
