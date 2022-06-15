package com.swift.soil.dto.post.response;

import com.swift.soil.entity.tag.Tag;
import lombok.Builder;
import lombok.Data;

@Data
public class FindTagRes {
    private String tagName;
    private int tagCnt;

    @Builder
    public FindTagRes(String tagName, int tagCnt) {
        this.tagName = tagName;
        this.tagCnt = tagCnt;
    }

    public static FindTagRes of(Tag tag) {
        return FindTagRes.builder()
                .tagName(tag.getTagName())
                .tagCnt(tag.getTagCnt())
                .build();
    }
}
