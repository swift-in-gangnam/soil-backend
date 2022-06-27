package com.swift.soil.service;

import com.swift.soil.dto.post.response.FindPostRes;
import com.swift.soil.dto.post.response.FindTagRes;
import com.swift.soil.dto.user.response.SearchUserRes;
import com.swift.soil.entity.post.PostRepository;
import com.swift.soil.entity.tag.Tag;
import com.swift.soil.entity.tag.TagPostRepository;
import com.swift.soil.entity.tag.TagRepository;
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
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class SearchService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final TagPostRepository tagPostRepository;
    private final FileService fileService;

    public List<SearchUserRes> searchUser(String keyword) {
        List<SearchUserRes> result = new ArrayList<>();
        for (User user : userRepository.findTop20ByNicknameStartsWith(keyword)) {
            SearchUserRes searchUserRes = SearchUserRes.of(user);

            if (!user.getProfileImageUrl().equals("empty"))
                searchUserRes.setProfileImageUrl(fileService.getFileUrl(user.getProfileImageUrl()));
            result.add(searchUserRes);
        }
        return result;
    }

    public List<FindTagRes> searchTag(String keyword) {
        return tagRepository.findTop20ByTagNameStartsWith(keyword).stream()
                .map(FindTagRes::of).collect(Collectors.toList());
    }

    public List<FindPostRes> searchPostByTag(Pageable pageable, String tagName) {
        Optional<Tag> tag = tagRepository.getTagByTagName(tagName);

        if (tag.isEmpty())
            throw new CustomException(ExceptionCode.TAG_NOT_FOUND);

        return postRepository.findByIdIn(tagPostRepository.getPostByTag(pageable, tag.get().getId())).stream()
                .map(FindPostRes::of).collect(Collectors.toList());
    }
}