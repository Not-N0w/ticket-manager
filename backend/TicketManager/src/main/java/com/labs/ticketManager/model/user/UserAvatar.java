package com.labs.ticketManager.model.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAvatar {
    private MultipartFile file;
}
