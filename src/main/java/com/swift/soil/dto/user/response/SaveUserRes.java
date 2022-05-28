package com.swift.soil.dto.user.response;

import com.swift.soil.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class SaveUserRes {

    private Long id;

    @Builder
    public SaveUserRes(Long id) {
        this.id = id;
    }

    public static SaveUserRes of(User user) {
        return SaveUserRes.builder()
                .id(user.getId())
                .build();
    }
}