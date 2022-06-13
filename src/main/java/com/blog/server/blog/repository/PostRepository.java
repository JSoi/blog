package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
