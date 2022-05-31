package com.swift.soil.entity.tag;

import com.swift.soil.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagPostRepository extends JpaRepository<TagPost, Long> {

    @Query(value = "DELETE * FROM tagpost WHERE post_id := post.id", nativeQuery = true)
    void deleteTagPostsByPost(Post post);
}