package com.example.unitech.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {
    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return FORBIDDEN;
    }
}
