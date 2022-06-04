package com.swift.soil.dto.user.response;

import com.swift.soil.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchUserRes {

	private String uid;
	private String nickname;
	private String name;
	private String profileImageUrl;

	public static SearchUserRes of(User user) {
		return SearchUserRes.builder()
				.uid(user.getUid())
				.nickname(user.getNickname())
				.name(user.getName())
				.profileImageUrl(user.getProfileImageUrl())
				.build();
	}

	@Builder
	public SearchUserRes(String uid, String nickname, String name, String profileImageUrl) {
		this.uid = uid;
		this.nickname = nickname;
		this.name = name;
		this.profileImageUrl = profileImageUrl;
	}
}