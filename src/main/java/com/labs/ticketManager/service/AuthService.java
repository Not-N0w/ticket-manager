package com.labs.ticketManager.service;

import com.labs.ticketManager.web.controller.auth.AuthenticationRequest;
import com.labs.ticketManager.web.controller.auth.AuthenticationResponse;
import com.labs.ticketManager.web.controller.auth.RegisterRequest;


public interface AuthService {

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public AuthenticationResponse register(RegisterRequest registerRequest);

}
