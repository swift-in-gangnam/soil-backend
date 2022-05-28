package com.swift.soil.service;

import com.swift.soil.dto.follow.SaveFollowRes;
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
    private final EntityManager em;

    public SaveFollowRes saveFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        if (!followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get()).isEmpty()) {
            throw new CustomException(ExceptionCode.ALREADY_FOLLOW);
        }

        return SaveFollowRes.of(followRepository.save(Follow.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build()));
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
}