package com.swift.soil.service;

import com.swift.soil.dto.post.response.FindTagRes;
import com.swift.soil.entity.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    // SearchController
    public List<FindTagRes> searchTag(String keyword) {
        return tagRepository.findTop20ByTagNameStartsWith(keyword).stream()
                .map(FindTagRes::of).collect(Collectors.toList());
    }
}