package com.swift.soil.service;

import com.swift.soil.dto.follow.FollowRes;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.entity.follow.Follow;
import com.swift.soil.entity.follow.FollowRepository;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
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

        FindUserRes findUserRes = FindUserRes.of(follow.getToUser());
        findUserRes.setType(2); // 팔로우 했기 때문에 2로 설정

        if (!follow.getToUser().getProfileImageUrl().equals("empty"))
            findUserRes.setProfileImageUrl(fileService.getFileUrl(follow.getToUser().getProfileImageUrl()));

        return findUserRes;
    }

    public boolean isFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        Optional<Follow> follow = followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get());

        return !(follow.isEmpty());
    }

    public FindUserRes unFollow(String fromUid, String toUid) {
        Optional<User> fromUser = userRepository.getUserByUid(fromUid);
        Optional<User> toUser = userRepository.getUserByUid(toUid);

        if (fromUser.isEmpty() || toUser.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        Optional<Follow> del = followRepository.getFollowByFromUserAndToUser(fromUser.get(), toUser.get());

        if (del.isEmpty())
            throw new CustomException(ExceptionCode.NOT_FOLLOW);

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

    public List<FollowRes> getFollower(String loginUid, String profileUid) {
        Optional<User> loginUser = userRepository.getUserByUid(loginUid);
        Optional<User> profileUser = userRepository.getUserByUid(profileUid);

        if (loginUser.isEmpty() || profileUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.nickname, u.profile_image_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((? = u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.from_user_id AND f.to_user_id = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginUser.get().getId())
                .setParameter(2, loginUser.get().getId())
                .setParameter(3, profileUser.get().getId());

        JpaResultMapper result = new JpaResultMapper();

        return result.list(query, FollowRes.class);
    }

    public List<FollowRes> getFollowing(String loginUid, String profileUid) {
        Optional<User> loginUser = userRepository.getUserByUid(loginUid);
        Optional<User> profileUser = userRepository.getUserByUid(profileUid);

        if (loginUser.isEmpty() || profileUser.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.nickname, u.profile_image_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((? = u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginUser.get().getId())
                .setParameter(2, loginUser.get().getId())
                .setParameter(3, profileUser.get().getId());

        JpaResultMapper result = new JpaResultMapper();

        return result.list(query, FollowRes.class);
    }
}