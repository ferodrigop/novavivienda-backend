package com.novavivienda.backend.mappers.user;

import com.novavivienda.backend.dtos.user.UserDto;
import com.novavivienda.backend.entities.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
