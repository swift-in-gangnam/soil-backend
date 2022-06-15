package com.swift.soil.controller;

import com.swift.soil.common.BaseResponse;
import com.swift.soil.common.ResponseCode;
import com.swift.soil.dto.post.response.FindPostRes;
import com.swift.soil.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/timeline")
@RestController
public class TimelineController extends DecodingUid {

    private final TimelineService timelineService;

    @GetMapping("/post/day")
    public ResponseEntity<BaseResponse> getUserPost(@RequestHeader(value = "Authorization") String auth, @RequestParam String userUid, @RequestParam(defaultValue = "0") int page) {
        tokenDecoding(auth);
        Pageable pageable = PageRequest.of(page, 10);
        List<FindPostRes> findPostResList = timelineService.getUserPost(pageable, userUid);
        return BaseResponse.toResponseEntity(findPostResList, ResponseCode.GET_DETAIL_POST_SUCCESS);
    }
}
