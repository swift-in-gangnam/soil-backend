package com.swift.soil.entity.user;

import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.entity.BaseTimeEntity;
import com.swift.soil.entity.follow.Follow;
import com.swift.soil.entity.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uid;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(length = 20)
    private String name;

    private String profileImageUrl;

    private String bio;

    private String fcmToken;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.REMOVE)
    private final List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.REMOVE)
    private final List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<Post> postList = new ArrayList<>();

    public int getFollowerCnt() {
        return this.followerList.size();
    }

    public int getFollowingCnt() {
        return this.followingList.size();
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateProfile(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public static User create(SaveUserReq saveUserReq) {
        return User.builder()
                .uid(saveUserReq.getUid())
                .email(saveUserReq.getEmail())
                .nickname(saveUserReq.getNickname())
                .name(saveUserReq.getName())
                .profileImageUrl(saveUserReq.getProfileImageUrl())
                .fcmToken(saveUserReq.getFcmToken())
                .build();
    }

    @Builder
    public User(String uid, String email, String nickname, String name, String profileImageUrl, String fcmToken) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
    }
}
