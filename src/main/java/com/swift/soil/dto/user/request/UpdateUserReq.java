package com.swift.soil.dto.user.request;

import lombok.Data;

@Data
public class UpdateUserReq {

    private String name;

    private String bio;

    private Boolean isDelete;
}