package com.novavivienda.backend.repositories.password_reset;

import com.novavivienda.backend.entities.password_reset.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    Optional<PasswordReset> findByIdAndExpiresAtAfter(UUID id, Instant date);
}
