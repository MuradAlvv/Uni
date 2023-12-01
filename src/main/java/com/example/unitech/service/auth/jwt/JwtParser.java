package com.example.unitech.service.auth.jwt;

import io.jsonwebtoken.Claims;

import org.springframework.security.core.Authentication;

public interface JwtParser {
    Claims parseClaims(String token);

    Authentication getAuthentication(String token);

    Long getUserId(String token);

    Long getUserIdFromBearer(String bearer);
}
