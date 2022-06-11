package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

}
