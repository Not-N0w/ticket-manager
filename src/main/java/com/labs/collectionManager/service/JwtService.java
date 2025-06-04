package com.labs.collectionManager.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

public interface JwtService {
    public String extractUsername(String token);
    public String generateToken(UserDetails userDetails);
    public String generateToken(Map<String, Object> claims, UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
    public boolean isTokenExpired(String token);
    public Claims extractAllClaims(String token);
}
