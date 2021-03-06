package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findAllByOrderByLikeCountDesc();
    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    void updateView(Long id);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + :value where p.id = :id")
    void updateLikeCount(Long id, Long value);

}
