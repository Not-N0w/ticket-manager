package com.labs.ticketManager.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public interface FileService {
    public void importData(MultipartFile file,String authHeader);
    public ByteArrayInputStream load();
}
