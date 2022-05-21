package com.swift.soil.service;

import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.dto.user.response.SaveUserRes;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    public SaveUserRes createUser(MultipartFile multipartFile, SaveUserReq saveUserReq) {
        if (!multipartFile.isEmpty())
            saveUserReq.setProfileImageUrl(fileService.uploadFile(multipartFile));
        else
            saveUserReq.setProfileImageUrl("empty");

        User user = userRepository.save(User.create(saveUserReq));
        return SaveUserRes.of(user);
    }

    public Optional<User> getUserInfo(String uid) {
        return userRepository.getUserByUid(uid);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    /*
    * 중복확인 관련 로직*/
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<User> getUserByNickName(String nickName) {
        return userRepository.getUserByNickname(nickName);
    }
}