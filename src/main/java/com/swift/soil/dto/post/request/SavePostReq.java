package com.swift.soil.dto.post.request;

import com.swift.soil.entity.user.User;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class SavePostReq {

    private String title;

    private boolean isSecret;

    private String content;

    private String imageUrl;

    private List<String> tags = new ArrayList<>();

    private String song;

    private User user;
}