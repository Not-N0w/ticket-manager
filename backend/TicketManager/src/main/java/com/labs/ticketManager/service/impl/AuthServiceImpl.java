package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.Status;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.repository.UserRepository;
import com.labs.ticketManager.service.AuthService;
import com.labs.ticketManager.web.controller.auth.AuthenticationRequest;
import com.labs.ticketManager.web.controller.auth.AuthenticationResponse;
import com.labs.ticketManager.web.controller.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        var user = User.builder()
                .username(registerRequest.getUsername())
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(new HashSet<>(Collections.singleton(Role.ROLE_USER)))
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
