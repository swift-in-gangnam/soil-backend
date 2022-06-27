package com.swift.soil.entity.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM post WHERE post.user_id = :userId ORDER BY created DESC", nativeQuery = true)
    List<Post> getPostByUserUid(Pageable pageable, @Param("userId") Long userId);

    List<Post> findByIdIn(List<Long> ids);
}
