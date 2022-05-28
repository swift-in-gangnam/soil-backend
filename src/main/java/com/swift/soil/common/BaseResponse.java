package com.swift.soil.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class BaseResponse<T> {

    private final int code;
    private final Boolean success;
    private final String message;
    private Object data;

    public static ResponseEntity<BaseResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .code(responseCode.getHttpStatus().value())
                        .success(true)
                        .message(responseCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<BaseResponse> toResponseEntity(Object data, ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .code(responseCode.getHttpStatus().value())
                        .success(true)
                        .message(responseCode.getMessage())
                        .data(data)
                        .build()
                );
    }
}