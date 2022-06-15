package com.swift.soil.dto.post.request;

import com.swift.soil.entity.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class SavePostReq {

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    private boolean isSecret;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    private String imageUrl;

    private List<String> tags = new ArrayList<>();

    private String song;

    private User user;
}