package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Likes;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUser(User user);
    Optional<Likes> findByPostAndUser(Post post, User user);
    boolean existsLikesByPostAndUser(Post post, User user);
}
