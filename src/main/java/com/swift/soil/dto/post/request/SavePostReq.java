package com.swift.soil.dto.post.request;

import com.swift.soil.entity.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SavePostReq {

    private String title;

    private boolean isSecret;

    private String content;

    private String imageUrl;

    private List<String> tags;

    private String song;

    private User user;

    @Builder
    public SavePostReq(String title, boolean isSecret, String content, String song) {
        this.title = title;
        this.isSecret = isSecret;
        this.content = content;
        this.song = song;
    }
}