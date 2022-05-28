package com.swift.soil.service;

import com.swift.soil.dto.user.request.SaveUserReq;
import com.swift.soil.dto.user.response.FindUserRes;
import com.swift.soil.dto.user.response.SaveUserRes;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
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

    /*
    * AuthController
    */
    public void login(String uid, String fcmToken) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        user.get().updateFcmToken(fcmToken);
        userRepository.save(user.get());
    }

    public void logout(String uid) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        user.get().updateFcmToken("logout");
        userRepository.save(user.get());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<User> getUserByNickName(String nickName) {
        return userRepository.getUserByNickname(nickName);
    }

    /*
    * UserController
    */
    public SaveUserRes createUser(MultipartFile multipartFile, SaveUserReq saveUserReq) {
        if (!multipartFile.isEmpty())
            saveUserReq.setProfileImageUrl(fileService.uploadFile(multipartFile));
        else
            saveUserReq.setProfileImageUrl("empty");

        User user = userRepository.save(User.create(saveUserReq));
        return SaveUserRes.of(user);
    }

    public FindUserRes getUserInfo(String uid) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty()) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        FindUserRes findUserRes = FindUserRes.of(user.get());

        if (!user.get().getProfileImageUrl().equals("empty"))
            findUserRes.setProfileImageUrl(fileService.getFileUrl(user.get().getProfileImageUrl()));

        return findUserRes;
    }
}