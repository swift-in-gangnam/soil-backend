package com.swift.soil.dto.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swift.soil.entity.emoji.Emoji;
import com.swift.soil.entity.post.Post;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class FindPostRes {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_content")
    private String postContent;

    @JsonProperty("post_image_url")
    private String postImageUrl;

    @JsonProperty("post_created_time")
    private LocalDate postCreatedTime;

    @JsonProperty("post_song")
    private String postSong;

    @JsonProperty("Emoji_info")
    private EmojiRes emojiRes;

    private List<FindTagRes> tags = new ArrayList<>();

    public static FindPostRes of(Post post) {
        return FindPostRes.builder()
                .userId(post.getUser().getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .postImageUrl(post.getProfileImageUrl())
                .postCreatedTime(post.getCreated())
                .postSong(post.getSong())
                .emoji(post.getEmoji())
                .build();
    }

    @Builder
    public FindPostRes(Long userId, String postTitle, String postContent, String postImageUrl, LocalDate postCreatedTime, String postSong, Emoji emoji) {
        this.userId = userId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
        this.postCreatedTime = postCreatedTime;
        this.postSong = postSong;
        this.emojiRes = EmojiRes.of(emoji);
    }
}
