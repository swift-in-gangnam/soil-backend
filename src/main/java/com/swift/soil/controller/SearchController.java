package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.service.PostService;
import com.swift.soil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController extends DecodingUid {

    private final UserService userService;
    private final PostService postService;

    // 사용자 닉네임으로 검색 및 게시글 태그로 검색
    public ResponseEntity<BaseResponse> searchUser(@RequestHeader(value = "Authorization") String auth, @RequestParam String query, @RequestParam String type) {
        tokenDecoding(auth);

//        if (type.equals("user"))
            return BaseResponse.toResponseEntity(userService.searchUser(query), ResponseCode.SEARCH_USER_SUCCESS);
//        else if (type.equals("tag"))
//            return BaseResponse
    }

}
