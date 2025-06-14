package com.labs.ticketManager.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAvatarDto {
    @NotNull(message = "Avatar must be not null")
    private MultipartFile file;

}
