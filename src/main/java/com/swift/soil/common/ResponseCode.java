package com.swift.soil.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * 에러 코드 관리
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /*
    * 중복 체크*/
    DUPLICATE_EMAIL(BAD_REQUEST, "이미 사용중인 이메일입니다."),
    NOT_DUPLICATE_EMAIL(OK, "사용 가능한 이메일입니다."),
    DUPLICATE_NICKNAME(BAD_REQUEST, "이미 사용중인 닉네임입니다."),
    NOT_DUPLICATE_NICKNAME(OK, "사용 가능한 닉네임입니다."),

    LOGIN_SUCCESS(OK, "로그인 완료했습니다."),
    LOGOUT_SUCCESS(OK, "로그아웃 완료했습니다."),
    JOIN_SUCCESS(OK, "회원가입 완료했습니다"),
    GET_USER_SUCCESS(OK, "유저정보 조회 완료했습니다")


    ;

    private final HttpStatus httpStatus;
    private final String message;

}