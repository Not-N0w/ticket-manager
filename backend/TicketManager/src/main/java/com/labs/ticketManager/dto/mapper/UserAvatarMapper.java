package com.labs.ticketManager.dto.mapper;

import com.labs.ticketManager.model.user.UserAvatar;
import com.labs.ticketManager.dto.user.UserAvatarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserAvatarMapper.class})
public interface UserAvatarMapper {
    UserAvatarDto toDto(UserAvatar userAvatar);
    UserAvatar toEntity(UserAvatarDto userAvatarDto);
}
