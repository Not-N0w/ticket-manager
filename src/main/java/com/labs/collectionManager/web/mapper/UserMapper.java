package com.labs.collectionManager.web.mapper;


import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto user);
}
