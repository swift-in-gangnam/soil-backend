package com.swift.soil.dto.user.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SaveUserReq {

    private String uid;

    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;

    private String name;

    private String profileImageUrl;

    private String fcmToken;
}