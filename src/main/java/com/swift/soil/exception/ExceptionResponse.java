package com.swift.soil.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final Boolean success;
    private final String message;

    public static ResponseEntity<ExceptionResponse> toResponseEntity(ExceptionCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .success(false)
                        .message(errorCode.getMessage())
                        .build()
                );
    }
}
