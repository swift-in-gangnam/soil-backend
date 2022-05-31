package com.swift.soil.service;

import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.dto.user.request.UpdateUserReq;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    // AuthController
    public void login(String uid, String fcmToken) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        user.get().updateFcmToken(fcmToken);
        userRepository.save(user.get());
    }

    public void logout(String uid) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        user.get().updateFcmToken("logout");
        userRepository.save(user.get());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<User> getUserByNickName(String nickName) {
        return userRepository.getUserByNickname(nickName);
    }

    // UserController
    public void createUser(MultipartFile multipartFile, SaveUserReq saveUserReq) {
        if (!multipartFile.isEmpty())
            saveUserReq.setProfileImageUrl(fileService.uploadFile(multipartFile));
        else
            saveUserReq.setProfileImageUrl("empty");

        userRepository.save(User.create(saveUserReq));
    }

    public FindUserRes getUserInfo(String uid) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        FindUserRes findUserRes = FindUserRes.of(user.get());

        if (!user.get().getProfileImageUrl().equals("empty"))
            findUserRes.setProfileImageUrl(fileService.getFileUrl(user.get().getProfileImageUrl()));

        return findUserRes;
    }

    public void updateProfile(String uid, UpdateUserReq updateUserReq, MultipartFile file) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        if (updateUserReq.getIsDelete()) {
            fileService.deleteFile(user.get().getProfileImageUrl());
            user.get().updateImageUrl("empty");
        }
        else {
            if (!file.isEmpty()) {
                if (!(user.get().getProfileImageUrl().equals("empty"))) {
                    fileService.deleteFile(user.get().getProfileImageUrl());
                }
                user.get().updateImageUrl(fileService.uploadFile(file));
            }
        }

        user.get().updateProfile(updateUserReq.getName(), updateUserReq.getBio());
        userRepository.save(user.get());
    }

    // SearchController
    public List<FindUserRes> searchUser(String keyword) {
        return userRepository.findTop20ByNicknameStartsWith(keyword).stream()
                .map(FindUserRes::of).collect(Collectors.toList());
    }
}