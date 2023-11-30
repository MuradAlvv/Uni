package com.example.unitech.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return BAD_REQUEST;
    }
}
