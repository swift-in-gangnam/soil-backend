package com.swift.soil.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    VALIDATION_ERROR(BAD_REQUEST, "이메일과 닉네임은 필수 항목입니다."),
    DUPLICATE_ERROR(BAD_REQUEST, "이메일과 닉네임의 중복확인이 필요합니다."),
    ALREADY_FOLLOW(BAD_REQUEST, "이미 팔로우 중인 사용자입니다."),
    NOT_FOLLOW(BAD_REQUEST, "팔로우 중인 사용자가 아닙니다"),
    SEARCH_TYPE_ERROR(BAD_REQUEST, "검색 유형을 올바르지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료됐습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    TOKEN_NOT_IN_HEADER(NOT_FOUND, "헤더에 토큰 정보가 없습니다."),

    /* 409 CONFLICT : 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다");

    private final HttpStatus httpStatus;
    private final String message;
}