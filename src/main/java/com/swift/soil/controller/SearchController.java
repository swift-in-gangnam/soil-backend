package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import com.swift.soil.service.TagService;
import com.swift.soil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController extends DecodingUid {

    private final UserService userService;
    private final TagService tagService;

    // 사용자 닉네임 목록 및 태그 목록 검색
    @GetMapping("")
    public ResponseEntity<BaseResponse> searchUser(@RequestHeader(value = "Authorization") String auth, @RequestParam String query, @RequestParam String type) {
        tokenDecoding(auth);

        if (type.equals("user"))
            return BaseResponse.toResponseEntity(userService.searchUser(query), ResponseCode.SEARCH_USER_SUCCESS);
        else if (type.equals("tag"))
            return BaseResponse.toResponseEntity(tagService.searchTag(query), ResponseCode.SEARCH_TAG_SUCCESS);
        else
            throw new CustomException(ExceptionCode.SEARCH_TYPE_ERROR);
    }
}
