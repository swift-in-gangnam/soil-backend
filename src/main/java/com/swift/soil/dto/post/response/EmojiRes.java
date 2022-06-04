package com.swift.soil.dto.post.response;

import com.swift.soil.entity.emoji.Emoji;
import com.swift.soil.entity.post.Post;
import lombok.Builder;
import lombok.Data;

@Data
public class EmojiRes {

    private int emoji1;
    private int emoji2;
    private int emoji3;
    private int emoji4;
    private int emoji5;
    private int emoji6;
    private int emoji7;

    public static EmojiRes of(Emoji emoji) {
        return EmojiRes.builder()
                .emoji1(emoji.getEmoji1())
                .emoji2(emoji.getEmoji2())
                .emoji3(emoji.getEmoji3())
                .emoji4(emoji.getEmoji4())
                .emoji5(emoji.getEmoji5())
                .emoji6(emoji.getEmoji6())
                .emoji7(emoji.getEmoji7())
                .build();
    }

    @Builder
    public EmojiRes(int emoji1, int emoji2, int emoji3, int emoji4, int emoji5, int emoji6, int emoji7) {
        this.emoji1 = emoji1;
        this.emoji2 = emoji2;
        this.emoji3 = emoji3;
        this.emoji4 = emoji4;
        this.emoji5 = emoji5;
        this.emoji6 = emoji6;
        this.emoji7 = emoji7;
    }
}
