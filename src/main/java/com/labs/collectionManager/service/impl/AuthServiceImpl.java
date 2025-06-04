package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.Status;
import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.repository.UserRepository;
import com.labs.collectionManager.service.AuthService;
import com.labs.collectionManager.web.controller.auth.AuthenticationRequest;
import com.labs.collectionManager.web.controller.auth.AuthenticationResponse;
import com.labs.collectionManager.web.controller.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
