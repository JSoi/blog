package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Likes;
import com.blog.server.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUser(User user);
}
