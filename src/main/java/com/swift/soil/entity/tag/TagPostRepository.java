package com.swift.soil.entity.tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TagPostRepository extends JpaRepository<TagPost, Long> {

    @Query(value = "SELECT post_id FROM tagpost WHERE tagpost.tag_id = :tagId ORDER BY created DESC", nativeQuery = true)
    List<Long> getPostByTag(Pageable pageable, @Param("tagId") Long tagId);
}