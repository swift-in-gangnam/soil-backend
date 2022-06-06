package com.swift.soil.entity.emoji;

import com.swift.soil.entity.post.Post;
import lombok.Getter;
import javax.persistence.*;

@Getter
@Entity
public class Emoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int emoji1;
    private int emoji2;
    private int emoji3;
    private int emoji4;
    private int emoji5;
    private int emoji6;
    private int emoji7;

    @OneToOne(fetch = FetchType.EAGER)
    private Post post;

    public void mappingPost(Post post) {
        this.post = post;
    }

    public void upEmoji(Integer emojiType) {
        if (emojiType == 1) this.emoji1++;
        else if (emojiType == 2) this.emoji2++;
        else if (emojiType == 3) this.emoji3++;
        else if (emojiType == 4) this.emoji4++;
        else if (emojiType == 5) this.emoji5++;
        else if (emojiType == 6) this.emoji6++;
        else if (emojiType == 7) this.emoji7++;
    }
}
