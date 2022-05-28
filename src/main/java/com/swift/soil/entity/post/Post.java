package com.swift.soil.entity.post;

import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String profileImageUrl;

    private LocalDate created;

    private LocalDate modified;

    private boolean isReported;

    private boolean isBlocked;

    private boolean isSecret;

    private String song;

    public static Post create(SavePostReq savepostReq) {
        return Post.builder()
                .title(savepostReq.getTitle())
                .isSecret(savepostReq.isSecret())
                .content(savepostReq.getContent())
                .profileImageUrl(savepostReq.getImageUrl())
                .song(savepostReq.getSong())
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .build();
    }

    @Builder
    public Post(String title, String content, String profileImageUrl, LocalDate created, LocalDate modified, boolean isReported, boolean isBlocked, boolean isSecret, String song) {
        this.title = title;
        this.content = content;
        this.profileImageUrl = profileImageUrl;
        this.created = created;
        this.modified = modified;
        this.isReported = isReported;
        this.isBlocked = isBlocked;
        this.isSecret = isSecret;
        this.song = song;
    }
}