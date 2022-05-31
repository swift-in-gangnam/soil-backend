package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController extends DecodingUid {

    private final PostService postService;

    public ResponseEntity<BaseResponse> uploadPost(@RequestHeader(value = "Authorization") String auth, SavePostReq savePostReq, @RequestParam(value = "file") MultipartFile multipartFile) {
        String uid = tokenDecoding(auth);
        postService.upload(uid, savePostReq, multipartFile);
        return BaseResponse.toResponseEntity(ResponseCode.UPLOAD_POST_SUCCESS);
    }
}