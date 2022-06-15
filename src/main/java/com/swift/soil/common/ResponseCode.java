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

    // AuthController
    DUPLICATE_EMAIL(BAD_REQUEST, "이미 사용중인 이메일입니다."),
    NOT_DUPLICATE_EMAIL(OK, "사용 가능한 이메일입니다."),
    DUPLICATE_NICKNAME(BAD_REQUEST, "이미 사용중인 닉네임입니다."),
    NOT_DUPLICATE_NICKNAME(OK, "사용 가능한 닉네임입니다."),
    LOGIN_SUCCESS(OK, "로그인 완료했습니다."),
    LOGOUT_SUCCESS(OK, "로그아웃 완료했습니다."),

    // UserController
    JOIN_SUCCESS(OK, "회원가입 완료했습니다"),
    GET_USER_SUCCESS(OK, "유저정보 조회 완료했습니다"),
    UPDATE_USER_SUCCESS(OK, "유저의 정보를 성공적으로 변경했습니다."),

    // FollowController
    FOLLOW_SUCCESS(OK, "팔로잉 완료했습니다."),
    UNFOLLOW_SUCCESS(OK, "언팔로잉 완료했습니다"),
    GET_FOLLOWER(OK, "팔로워 목록을 불러왔습니다."),
    GET_FOLLOWING(OK, "팔로잉 목록을 불러왔습니다."),

    // PostController
    UPLOAD_POST_SUCCESS(OK, "포스트 업로드를 완료했습니다."),
    GET_POST_LIST_SUCCESS(OK, "포스트 가져오기에 성공하였습니다."),
    GET_DETAIL_POST_SUCCESS(OK, "포스트 상세조회를 완료했습니다."),
    ADD_EMOJI_SUCCESS(OK, "이모지 반응에 성공하였습니다."),
    DELETE_POST_SUCCESS(OK, "포스트 삭제에 성공하였습니다."),

    // SearchController
    SEARCH_USER_SUCCESS(OK, "유저 검색을 완료했습니다"),
    SEARCH_TAG_SUCCESS(OK, "태그 검색을 완료했습니다"),




            ;

    private final HttpStatus httpStatus;
    private final String message;

}