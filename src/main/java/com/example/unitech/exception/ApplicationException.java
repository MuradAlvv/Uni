package com.example.unitech.exception;

import org.springframework.http.HttpStatusCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public HttpStatusCode getStatusCode() {
        return BAD_REQUEST;
    }
}
