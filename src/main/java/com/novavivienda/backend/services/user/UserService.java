package com.novavivienda.backend.services.user;

import com.novavivienda.backend.dtos.auth.SignUpRequestDto;
import com.novavivienda.backend.entities.user.RoleName;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.NotFoundException;
import com.novavivienda.backend.repositories.user.UserRepository;
import com.novavivienda.backend.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PreAuthorize("principal.id == #userId or hasRole('MANAGER')")
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User createNewUser(SignUpRequestDto signUpRequestDto, RoleName roleName) {
        User newUser = new User();
        newUser.setFirstName(signUpRequestDto.firstName());
        newUser.setLastName(signUpRequestDto.lastName());
        newUser.setEmail(signUpRequestDto.email());
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.password()));
        newUser.setRole(roleService.getRoleByRoleName(roleName));
        newUser.setEmailVerified(false);
        newUser.setDeleted(false);
        return userRepository.saveAndFlush(newUser);
    }
}
