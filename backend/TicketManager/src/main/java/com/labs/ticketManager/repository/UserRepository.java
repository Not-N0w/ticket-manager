package com.labs.ticketManager.repository;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
