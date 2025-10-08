package com.novavivienda.backend.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "role_permission")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Permission name is required")
    @Size(max = 50, message = "Permission name must not exceed 50 characters")
    @Column(nullable = false, unique = true)
    private String permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
}
