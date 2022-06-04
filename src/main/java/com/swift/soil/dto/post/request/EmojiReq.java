package com.swift.soil.dto.post.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmojiReq {

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("emoji_type")
    private Integer emojiType;
}
