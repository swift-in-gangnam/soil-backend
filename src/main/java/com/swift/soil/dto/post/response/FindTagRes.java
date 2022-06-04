package com.swift.soil.dto.post.response;

import com.swift.soil.entity.tag.Tag;
import lombok.Builder;
import lombok.Data;

@Data
public class FindTagRes {
    private String tagName;

    @Builder
    public FindTagRes(String tagName) {
        this.tagName = tagName;
    }

    public static FindTagRes of(Tag tag) {
        return FindTagRes.builder()
                .tagName(tag.getTagName())
                .build();
    }
}
