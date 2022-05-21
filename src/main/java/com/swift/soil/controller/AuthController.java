package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.entity.user.User;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import com.swift.soil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController extends DecodingUid {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestHeader(value = "Authorization") String auth, @RequestBody String fcmToken) {
        String uid = tokenDecoding(auth);

        User user = userService.getUserInfo(uid).orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
        user.updateFcmToken(fcmToken);
        userService.update(user);

        return BaseResponse.toResponseEntity(ResponseCode.LOGIN_SUCCESS);

    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout(@RequestHeader(value = "Authorization") String auth) {
        String uid = tokenDecoding(auth);

        User user = userService.getUserInfo(uid).orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
        user.updateFcmToken("logout");
        userService.update(user);

        return BaseResponse.toResponseEntity(ResponseCode.LOGOUT_SUCCESS);
    }

    @GetMapping("/auth/dupEmail/{email}")
    public ResponseEntity<BaseResponse> dupEmail(@PathVariable String email) {
        try {
            userService.getUserByEmail(email).orElseThrow(() -> new IllegalArgumentException());
        }
        catch (IllegalArgumentException e) {
            return BaseResponse.toResponseEntity(ResponseCode.DUPLICATE_EMAIL);
        }
        return BaseResponse.toResponseEntity(ResponseCode.NOT_DUPLICATE_EMAIL);
    }

    @GetMapping("/auth/dupNickname/{nickname}")
    public ResponseEntity<BaseResponse> dupNickname(@PathVariable String nickname) {
        try {
            userService.getUserByNickName(nickname).orElseThrow(() -> new IllegalArgumentException());
        }
        catch (IllegalArgumentException e) {
            return BaseResponse.toResponseEntity(ResponseCode.DUPLICATE_NICKNAME);
        }
        return BaseResponse.toResponseEntity(ResponseCode.NOT_DUPLICATE_NICKNAME);
    }
}
