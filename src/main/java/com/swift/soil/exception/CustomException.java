package com.swift.soil.exception;

import com.google.firebase.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}