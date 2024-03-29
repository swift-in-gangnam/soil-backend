package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.dto.user.request.UpdateUserReq;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.service.FollowService;
import com.swift.soil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController extends DecodingUid {

    private final UserService userService;
    private final FollowService followService;

    // 회원가입
    @PostMapping
    public ResponseEntity<BaseResponse> join(@RequestHeader(value = "Authorization") String auth, @Valid SaveUserReq saveUserReq, @RequestParam(value = "file") MultipartFile multipartFile) {
        saveUserReq.setUid(tokenDecoding(auth));
        userService.createUser(multipartFile, saveUserReq);
        return BaseResponse.toResponseEntity(ResponseCode.JOIN_SUCCESS);
    }

    // 사용자 조회
    @GetMapping("/{userUid}")
    public ResponseEntity<BaseResponse> getUser(@RequestHeader(value = "Authorization") String auth, @PathVariable String userUid) {
        String uid = tokenDecoding(auth);
        FindUserRes findUser = userService.getUserInfo(userUid);

        /*
         * 1 = 본인
         * 2 = 팔로우
         * 3 = 팔로우 x
         */
        if (uid.equals(userUid))
            findUser.setType(1);
        else {
            if (followService.isFollow(uid, userUid)) {
                findUser.setType(2);
            } else
                findUser.setType(3);
        }
        return BaseResponse.toResponseEntity(findUser, ResponseCode.GET_USER_SUCCESS);
    }

    // 사용자 프로필 변경
    @PatchMapping("")
    public ResponseEntity<BaseResponse> updateProfile(@RequestHeader(value = "Authorization") String auth, UpdateUserReq updateUserReq, @RequestParam(value = "file") MultipartFile multipartFile) {
        String uid = tokenDecoding(auth);
        userService.updateProfile(uid, updateUserReq, multipartFile);
        return BaseResponse.toResponseEntity(ResponseCode.UPDATE_USER_SUCCESS);
    }
}