package com.example.unitech.configuration.security;

import com.example.unitech.service.auth.jwt.JwtParser;
import com.example.unitech.util.ErrorResponseHandler;
import com.example.unitech.util.SecurityUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtParser jwtProvider;
    private final ErrorResponseHandler responseHandler;

    @Override
    public void doFilterInternal(
            HttpServletRequest httpRequest,
            HttpServletResponse servletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            val token = SecurityUtil.extractToken(httpRequest);
            val authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpRequest, servletResponse);
        } catch (Exception e) {
            if (e instanceof JwtException) {
                if (e instanceof ExpiredJwtException) {
                    responseHandler.setResponse(servletResponse, "Jwt is expired");
                    return;
                }
                responseHandler.setResponse(servletResponse, "Invalid jwt");
                return;
            }
            filterChain.doFilter(httpRequest, servletResponse);
        }
    }
}
