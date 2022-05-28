package com.swift.soil.dto.follow;

import com.swift.soil.entity.follow.Follow;
import lombok.Builder;
import lombok.Data;

@Data
public class SaveFollowRes {

    private Long id;

    @Builder
    public SaveFollowRes(Long id) {
        this.id = id;
    }

    public static SaveFollowRes of(Follow follow) {
        return SaveFollowRes.builder()
                .id(follow.getId())
                .build();
    }
}