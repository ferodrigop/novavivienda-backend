package com.novavivienda.backend.services.role;

import com.novavivienda.backend.entities.user.Role;
import com.novavivienda.backend.entities.user.RoleName;
import com.novavivienda.backend.repositories.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByRoleName(RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }
}
