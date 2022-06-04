package com.swift.soil.service;

import com.swift.soil.dto.post.request.EmojiReq;
import com.swift.soil.dto.post.request.SavePostReq;
import com.swift.soil.dto.post.response.FindPostRes;
import com.swift.soil.dto.post.response.FindTagRes;
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

import java.util.List;
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
        else
            savePostReq.setImageUrl("empty");
        savePostReq.setUser(user.get());

        Post post = Post.create(savePostReq);
        post.createEmoji();
        postRepository.save(post);

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

    public FindPostRes getPost(Long postId) {
        Post post = postRepository.getById(postId);

        FindPostRes findPostRes = FindPostRes.of(post);
        if (!(post.getProfileImageUrl().equals("empty")))
            findPostRes.setPostImageUrl(fileService.getFileUrl(post.getProfileImageUrl()));

        for (TagPost t : post.getTagList())
            findPostRes.getTags().add(new FindTagRes(t.getTag().getTagName()));
        return findPostRes;
    }

    public void addEmoji(EmojiReq emojiReq) {
        Post post = postRepository.getById(emojiReq.getPostId());
        post.getEmoji().upEmoji(emojiReq.getEmojiType());
        postRepository.save(post);
    }
}
