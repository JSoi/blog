package com.blog.server.blog.repository;

import com.blog.server.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostPagingRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllBy(Pageable pageable);
}
