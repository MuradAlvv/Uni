package com.example.unitech.configuration.security;

import com.example.unitech.service.auth.JwtProvider;
import com.example.unitech.util.ErrorResponseHandler;
import com.example.unitech.util.SecurityUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilter {

    private final JwtProvider jwtProvider;
    private final ErrorResponseHandler responseHandler;

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        var httpRequest = (HttpServletRequest) servletRequest;
        if (("post".equalsIgnoreCase(httpRequest.getMethod())
                && httpRequest.getServletPath().contains("/users"))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                String token = SecurityUtil.extractToken(httpRequest);
                val authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception e) {
                if (e instanceof JwtException) {
                    if (e instanceof ExpiredJwtException) {
                        responseHandler.setResponse(servletResponse, "Jwt is expired");
                        return;
                    }
                    responseHandler.setResponse(servletResponse, "Invalid jwt");
                    return;
                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
