package com.labs.ticketManager.dto.mapper;

import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.dto.user.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDto(List<User> users);
}
