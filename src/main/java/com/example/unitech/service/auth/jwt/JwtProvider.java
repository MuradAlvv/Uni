package com.example.unitech.service.auth.jwt;

import org.springframework.security.core.Authentication;

public interface JwtProvider {

    String generateToken(Authentication authentication);
}
