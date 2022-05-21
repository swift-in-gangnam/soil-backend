package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.user.request.SaveUserReq;
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

    @PostMapping("")
    public ResponseEntity<BaseResponse> join(@RequestHeader(value = "Authorization") String auth, @Valid SaveUserReq saveUserReq, @RequestParam(value = "file") MultipartFile multipartFile) {
        saveUserReq.setUid(tokenDecoding(auth));
        userService.createUser(multipartFile, saveUserReq);
        return BaseResponse.toResponseEntity(ResponseCode.JOIN_SUCCESS);
    }
}
