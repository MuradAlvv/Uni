package com.example.unitech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    private ResponseEntity<?> handle(ApplicationException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatusCode());
    }
}
