package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.follow.FollowRes;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/follow")
@RestController
public class FollowController extends DecodingUid {

    private final FollowService followService;

    // 팔로우
    @PostMapping("/{followingUid}")
    public ResponseEntity<BaseResponse> followUser(@RequestHeader(value = "Authorization") String auth, @PathVariable String followingUid) {
        String uid = tokenDecoding(auth);
        FindUserRes findUserRes = followService.saveFollow(uid, followingUid);
        return BaseResponse.toResponseEntity(findUserRes, ResponseCode.FOLLOW_SUCCESS);
    }

    // 언팔로우
    @DeleteMapping("/{unFollowingUid}")
    public ResponseEntity<BaseResponse> unFollowUser(@RequestHeader(value = "Authorization") String auth, @PathVariable String unFollowingUid) {
        String uid = tokenDecoding(auth);
        FindUserRes findUserRes = followService.unFollow(uid, unFollowingUid);
        return BaseResponse.toResponseEntity(findUserRes, ResponseCode.UNFOLLOW_SUCCESS);
    }

    // 팔로워 목록 불러오기
    @GetMapping("/{profileUid}/follower")
    public ResponseEntity<BaseResponse> getFollower(@RequestHeader(value = "Authorization") String auth, @PathVariable String profileUid) {
        String uid = tokenDecoding(auth);
        List<FollowRes> followResList = followService.getFollower(uid, profileUid);
        return BaseResponse.toResponseEntity(followResList, ResponseCode.GET_FOLLOWER);
    }

    // 팔로잉 목록 불러오기
    @GetMapping("/{profileUid}/following")
    public ResponseEntity<BaseResponse> getFollowing(@RequestHeader(value = "Authorization") String auth, @PathVariable String profileUid) {
        String uid = tokenDecoding(auth);
        List<FollowRes> followResList = followService.getFollowing(uid, profileUid);
        return BaseResponse.toResponseEntity(followResList, ResponseCode.GET_FOLLOWING);
    }
}
