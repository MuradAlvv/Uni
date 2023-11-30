package com.example.unitech.util;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.unitech.dto.error.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorResponseHandler {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void setResponse(ServletResponse servletResponse, String message) {
        val response = (HttpServletResponse) servletResponse;
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(SC_UNAUTHORIZED);
        val bytes = objectMapper.writeValueAsBytes(new ErrorDto(message));
        response.getOutputStream().write(bytes);
    }
}
