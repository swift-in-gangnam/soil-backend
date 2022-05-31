package com.swift.soil.entity.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> getTagByTagName(String tagName);
    List<Tag> findTop20ByTagNameStartsWith(String keyword);
}