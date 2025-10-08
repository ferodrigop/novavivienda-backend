package com.novavivienda.backend.mappers.user;

import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserQualifier {
    private final UserRepository userRepository;

    @Named("idToUser")
    public User getUser(final UUID id) {
        return userRepository.getReferenceById(id);
    }
}
