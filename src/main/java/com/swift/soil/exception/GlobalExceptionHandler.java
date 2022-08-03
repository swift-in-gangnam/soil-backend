package com.swift.soil.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.swift.soil.exception.ExceptionCode.DUPLICATE_RESOURCE;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ExceptionResponse> handleDataException() {
        return ExceptionResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        return ExceptionResponse.toResponseEntity(e.getExceptionCode());
    }
}