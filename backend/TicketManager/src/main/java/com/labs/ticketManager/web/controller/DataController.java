package com.labs.ticketManager.web.controller;


import com.labs.ticketManager.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DataController {
    private final FileService fileService;

    @PostMapping("/import")
    public void importData(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader
    ) {
        fileService.importData(file, authHeader);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportData() {
        ByteArrayInputStream data = fileService.load();
        InputStreamResource resource = new InputStreamResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tickets.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}
