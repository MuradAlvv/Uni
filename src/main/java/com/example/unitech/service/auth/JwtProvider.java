package com.example.unitech.service.auth;

import org.springframework.security.core.Authentication;

public interface JwtProvider {

    String generateToken(Authentication authentication);

    Authentication getAuthentication(String token);
}
