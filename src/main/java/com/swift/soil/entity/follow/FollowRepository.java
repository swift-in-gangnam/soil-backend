package com.swift.soil.entity.follow;

import com.swift.soil.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> getFollowByFromUserAndToUser(User fromUser, User toUser);
}