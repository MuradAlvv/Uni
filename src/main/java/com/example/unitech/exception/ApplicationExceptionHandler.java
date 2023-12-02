package com.example.unitech.exception;

import static com.example.unitech.common.Errors.Auth.InvalidCredentialsError.INVALID_CREDENTIALS_ERROR;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.example.unitech.dto.error.ErrorDto;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    private ResponseEntity<?> handle(ApplicationException exception) {
        return new ResponseEntity<>(
                new ErrorDto(exception.getMessage()), exception.getStatusCode());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    private ResponseEntity<?> handle(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new ErrorDto(exception.getMessage()), exception.getStatusCode());
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    private ResponseEntity<?> handle(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    private ResponseEntity<?> handle() {
        return new ResponseEntity<>(
                new ErrorDto(INVALID_CREDENTIALS_ERROR.getMessage()), UNAUTHORIZED);
    }
}
