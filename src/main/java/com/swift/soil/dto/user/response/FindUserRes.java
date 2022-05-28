package com.swift.soil.dto.user.response;

import com.swift.soil.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class FindUserRes {

    private String uid;
    private String nickname;
    private String name;
    private String profileImageUrl;
    private String bio;
    private int followingCnt;
    private int followerCnt;
    private int type;

    public static FindUserRes of(User user) {
        return FindUserRes.builder()
                .uid(user.getUid())
                .nickname(user.getNickname())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .bio(user.getBio())
                .followingCnt(user.getFollowingCnt())
                .followerCnt(user.getFollowerCnt())
                .build();
    }

    @Builder
    public FindUserRes(String uid, String nickname, String name, String profileImageUrl, String bio, int followingCnt, int followerCnt) {
        this.uid = uid;
        this.nickname = nickname;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.followingCnt = followingCnt;
        this.followerCnt = followerCnt;
    }
}