package com.labs.collectionManager.service;

import com.labs.collectionManager.web.controller.auth.AuthenticationRequest;
import com.labs.collectionManager.web.controller.auth.AuthenticationResponse;
import com.labs.collectionManager.web.controller.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;


public interface AuthService {

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public AuthenticationResponse register(RegisterRequest registerRequest);

}
