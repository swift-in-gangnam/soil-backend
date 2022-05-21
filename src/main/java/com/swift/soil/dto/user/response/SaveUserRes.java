package com.swift.soil.dto.user.response;

import com.swift.soil.entity.user.User;
import lombok.Builder;

public class SaveUserRes {

    private Long id;

    @Builder
    public SaveUserRes(Long userId) {
        this.id = id;
    }

    public static SaveUserRes of(User user) {
        return SaveUserRes.builder()
                .userId(user.getId())
                .build();
    }
}