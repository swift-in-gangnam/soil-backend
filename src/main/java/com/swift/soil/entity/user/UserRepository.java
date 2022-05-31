package com.swift.soil.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUid(String uid);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByNickname(String nickname);
    List<User> findTop20ByNicknameStartsWith(String keyword);
}