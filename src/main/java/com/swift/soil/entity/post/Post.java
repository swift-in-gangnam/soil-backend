package com.swift.soil.entity.post;

import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.entity.BaseTimeEntity;
import com.swift.soil.entity.emoji.Emoji;
import com.swift.soil.entity.tag.TagPost;
import com.swift.soil.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String profileImageUrl;

    private boolean isReported;

    private boolean isBlocked;

    private boolean isSecret;

    private String song;

    private Month month;

    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Emoji emoji;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private final List<TagPost> tagList = new ArrayList<>();

    public void mappingEmoji(Emoji emoji) {
        this.emoji = emoji;
        emoji.mappingPost(this);
    }

    public static Post create(SavePostReq savepostReq) {
        return Post.builder()
                .title(savepostReq.getTitle())
                .isSecret(savepostReq.isSecret())
                .content(savepostReq.getContent())
                .profileImageUrl(savepostReq.getImageUrl())
                .song(savepostReq.getSong())
                .user(savepostReq.getUser())
                .build();
    }

    @Builder
    public Post(String title, String content, String profileImageUrl, boolean isReported, boolean isBlocked, boolean isSecret, String song, User user) {
        this.title = title;
        this.content = content;
        this.profileImageUrl = profileImageUrl;
        this.isReported = isReported;
        this.isBlocked = isBlocked;
        this.isSecret = isSecret;
        this.song = song;
        this.user = user;
        this.month = LocalDate.now().getMonth();
        this.year = LocalDate.now().getYear();
    }
}
