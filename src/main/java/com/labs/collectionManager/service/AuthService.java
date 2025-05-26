package com.labs.collectionManager.service;

import com.labs.collectionManager.web.dto.auth.JwtRequest;
import com.labs.collectionManager.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
