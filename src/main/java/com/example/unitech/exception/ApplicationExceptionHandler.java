package com.example.unitech.exception;

import com.example.unitech.dto.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    private ResponseEntity<?> handle(ApplicationException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), exception.getStatusCode());
    }
}
