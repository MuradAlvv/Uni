package com.example.unitech.util;

import static lombok.AccessLevel.PRIVATE;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.http.HttpServletRequest;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class SecurityUtil {

    private static final String BEARER = "Bearer ";

    public static String extractToken(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader(AUTHORIZATION);
        if (!bearer.startsWith(BEARER)) {
            throw new RuntimeException();
        }
        return extractToken(bearer);
    }

    public static String extractToken(String bearer) {
        return bearer.split("\\s")[1];
    }
}
