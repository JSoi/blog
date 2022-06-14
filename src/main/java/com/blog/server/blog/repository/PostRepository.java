package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("update Post p set p.view_count = p.view_count + 1 where p.id = :id")
    void updateView(Long id);
}
