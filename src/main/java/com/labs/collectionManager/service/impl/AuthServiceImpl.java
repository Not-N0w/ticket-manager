package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.service.AuthService;
import com.labs.collectionManager.web.dto.auth.JwtRequest;
import com.labs.collectionManager.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
