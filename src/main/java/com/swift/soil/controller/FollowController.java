package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/follow")
@RestController
public class FollowController extends DecodingUid{

    private final FollowService followService;

    @PostMapping("/{followingUid}")
    public ResponseEntity<BaseResponse> followUser(@RequestHeader(value = "Authorization") String auth, @PathVariable String followingUid) {
        String uid = tokenDecoding(auth);
        FindUserRes findUserRes = followService.saveFollow(uid, followingUid);
        return BaseResponse.toResponseEntity(findUserRes, ResponseCode.FOLLOW_SUCCESS);
    }

    @DeleteMapping("/{unFollowingUid}")
    public ResponseEntity<BaseResponse> unFollowUser(@RequestHeader(value = "Authorization") String auth, @PathVariable String unFollowingUid) {
        String uid = tokenDecoding(auth);
        FindUserRes findUserRes = followService.unFollow(uid, unFollowingUid);
        return BaseResponse.toResponseEntity(findUserRes, ResponseCode.UNFOLLOW_SUCCESS);
    }
}
