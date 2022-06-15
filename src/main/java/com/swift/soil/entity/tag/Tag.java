package com.swift.soil.entity.tag;

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
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private int tagCnt;

    @OneToMany(mappedBy = "tag")
    private final List<TagPost> PostList = new ArrayList<>();

    public static Tag create(String tagName) {
        return Tag.builder()
                .tagName(tagName)
                .build();
    }

    public void upCnt() {
        this.tagCnt++;
    }

    public int downCnt() {
        return this.tagCnt--;
    }

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
        this.tagCnt = 1;
    }
}