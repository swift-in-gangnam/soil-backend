package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.post.request.EmojiReq;
import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.dto.post.response.FindPostRes;
import com.swift.soil.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController extends DecodingUid {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> uploadPost(@RequestHeader(value = "Authorization") String auth, SavePostReq savePostReq, @RequestParam(value = "file") MultipartFile multipartFile) {
        String uid = tokenDecoding(auth);
        postService.upload(uid, savePostReq, multipartFile);
        return BaseResponse.toResponseEntity(ResponseCode.UPLOAD_POST_SUCCESS);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getAllPost(@RequestHeader(value = "Authorization") String auth, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        tokenDecoding(auth);
        Pageable pageable = PageRequest.of(page, size);
        List<FindPostRes> response = postService.getAllPost(pageable);
        return BaseResponse.toResponseEntity(response, ResponseCode.GET_POST_LIST_SUCCESS);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse> getPost(@RequestHeader(value = "Authorization") String auth, @PathVariable Long postId) {
        tokenDecoding(auth);
        FindPostRes findPostRes = postService.getPost(postId);
        return BaseResponse.toResponseEntity(findPostRes, ResponseCode.GET_DETAIL_POST_SUCCESS);
    }

    @PatchMapping("/emoji")
    public ResponseEntity<BaseResponse> addEmoji(@RequestHeader(value = "Authorization") String auth, @RequestBody EmojiReq emojiReq) {
        tokenDecoding(auth);
        postService.addEmoji(emojiReq);
        return BaseResponse.toResponseEntity(ResponseCode.ADD_EMOJI_SUCCESS);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse> deletePost(@RequestHeader(value = "Authorization") String auth, @PathVariable Long postId) {
        tokenDecoding(auth);
        postService.deletePost(postId);
        return BaseResponse.toResponseEntity(ResponseCode.DELETE_POST_SUCCESS);
    }
}