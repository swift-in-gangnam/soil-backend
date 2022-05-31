package com.swift.soil.service;

import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.entity.post.Post;
import com.swift.soil.entity.post.PostRepository;
import com.swift.soil.entity.tag.Tag;
import com.swift.soil.entity.tag.TagPost;
import com.swift.soil.entity.tag.TagPostRepository;
import com.swift.soil.entity.tag.TagRepository;
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
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagPostRepository tagPostRepository;
    private final TagRepository tagRepository;
    private final FileService fileService;

    //PostController
    public void upload(String uid, SavePostReq savePostReq, MultipartFile file) {
        Optional<User> user = userRepository.getUserByUid(uid);

        if (user.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);

        if (!file.isEmpty())
            savePostReq.setImageUrl(fileService.uploadFile(file));
        savePostReq.setUser(user.get());

        Post post = postRepository.save(Post.create(savePostReq));

        if (savePostReq.getTags().size() != 0) {
            for (String tagName : savePostReq.getTags()) {
                if (tagName.length() == 0)
                    continue;
                // 생성되어 있는 태그가 없다면 생성
                if (tagRepository.getTagByTagName(tagName).isEmpty()) {
                    Tag t = tagRepository.save(Tag.create(tagName));
                    tagPostRepository.save(new TagPost(post, t));
                }
                // 생성되어 있으면 바로 매핑
                else
                    tagPostRepository.save(new TagPost(post, tagRepository.getTagByTagName(tagName).get()));
            }
        }
    }
}
