package com.labs.ticketManager.config;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.Status;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminUsername = "super_admin";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = User.builder()
                    .username(adminUsername)
                    .firstName("Super")
                    .lastName("Admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Collections.singleton(Role.ROLE_ADMIN))
                    .status(Status.ACTIVE)
                    .build();

            userRepository.save(admin);
            System.out.println("Superuser created: " + adminUsername);
        } else {
            System.out.println("Superuser already exists.");
        }
    }
}
