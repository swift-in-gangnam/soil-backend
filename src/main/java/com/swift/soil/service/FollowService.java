package com.swift.soil.service;

import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.entity.follow.Follow;
import com.swift.soil.entity.follow.FollowRepository;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final EntityManager em;

    public FindUserRes saveFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        if (!followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get()).isEmpty()) {
            throw new CustomException(ExceptionCode.ALREADY_FOLLOW);
        }
        Follow follow = followRepository.save(Follow.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build());

        FindUserRes findUserRes = FindUserRes.builder()
                .uid(follow.getToUser().getUid())
                .nickname(follow.getToUser().getNickname())
                .name(follow.getToUser().getName())
                .bio(follow.getToUser().getBio())
                .profileImageUrl(follow.getToUser().getProfileImageUrl())
                .followingCnt(follow.getToUser().getFollowingCnt())
                .followerCnt(follow.getToUser().getFollowerCnt())
                .build();

        findUserRes.setType(2); // 팔로우 했기 때문에 2로 설정

        if (!follow.getToUser().getProfileImageUrl().equals("empty"))
            findUserRes.setProfileImageUrl(fileService.getFileUrl(follow.getToUser().getProfileImageUrl()));

        return findUserRes;
    }

    public boolean isFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        Optional<Follow> follow = followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get());

        return !(follow.isEmpty());
    }

    public FindUserRes unFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        Optional<Follow> del = followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get());
        if (del.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOLLOW);
        }
        followRepository.delete(del.get());

        FindUserRes findUserRes = FindUserRes.builder()
                .uid(toUser.get().getUid())
                .nickname(toUser.get().getNickname())
                .name(toUser.get().getName())
                .bio(toUser.get().getBio())
                .profileImageUrl(toUser.get().getProfileImageUrl())
                .followingCnt(toUser.get().getFollowingCnt())
                .followerCnt(toUser.get().getFollowerCnt())
                .build();

        findUserRes.setType(3); // 언팔로우 했기 때문에 3로 설정

        if (!toUser.get().getProfileImageUrl().equals("empty"))
            findUserRes.setProfileImageUrl(fileService.getFileUrl(toUser.get().getProfileImageUrl()));

        return findUserRes;
    }
}