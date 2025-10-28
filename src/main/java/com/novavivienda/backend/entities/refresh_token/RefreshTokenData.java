package com.novavivienda.backend.entities.refresh_token;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenData implements Serializable {
    private UUID id;
    private UUID userId;
    private Instant expiresAt;
    private Instant createdAt;
}
