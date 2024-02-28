package com.example.bpls_lab1.controller;

import com.example.bpls_lab1.dtos.ErrorResponseDTO;
import com.example.bpls_lab1.exception.FilmNotFoundException;
import com.example.bpls_lab1.exception.InsufficientSubscriptionLevelException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiAdvice {

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleFilmNotFoundException(FilmNotFoundException e) {
        return new ErrorResponseDTO("Film not found: " + e.getMessage());
    }

    @ExceptionHandler(InsufficientSubscriptionLevelException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDTO InsufficientSubscriptionLevelException(InsufficientSubscriptionLevelException e) {
        return new ErrorResponseDTO("You have insufficient subscription level: " + e.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO invalidLoginException(PSQLException e) {
        return new ErrorResponseDTO(e.getMessage());
    }
}
