package com.swift.soil.dto.follow;

import lombok.*;
import java.math.BigInteger;

@Data
public class FollowRes {

    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private int followState;
    private int loginUser;

    @Builder
    public FollowRes(BigInteger id, String name, String nickname, String profileImageUrl, int followState, int loginUser) {
        this.id = id.longValue();
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.followState = followState;
        this.loginUser = loginUser;
    }
}