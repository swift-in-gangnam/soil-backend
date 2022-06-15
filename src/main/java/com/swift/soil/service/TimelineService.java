package com.swift.soil.service;

import com.swift.soil.dto.post.response.FindPostRes;
import com.swift.soil.dto.post.response.FindTagRes;
import com.swift.soil.entity.post.Post;
import com.swift.soil.entity.post.PostRepository;
import com.swift.soil.entity.tag.TagPost;
import com.swift.soil.entity.user.User;
import com.swift.soil.entity.user.UserRepository;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TimelineService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FileService fileService;

    public List<FindPostRes> getUserPost(Pageable pageable, String userUid) {
        Optional<User> user = userRepository.getUserByUid(userUid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        List<Post> postList = postRepository.getPostByUserUid(pageable, user.get().getId());
        List<FindPostRes> findPostResList = new ArrayList<>();

        System.out.println("asfasdfad");
        for (Post p : postList) {
            FindPostRes findPostRes = FindPostRes.of(p);
            if (!(p.getProfileImageUrl().equals("empty")))
                findPostRes.setPostImageUrl(fileService.getFileUrl(p.getProfileImageUrl()));

            for (TagPost t : p.getTagList())
                findPostRes.getTags().add(new FindTagRes(t.getTag().getTagName(), t.getTag().getTagCnt()));
            findPostResList.add(findPostRes);
        }
        return findPostResList;
    }
}