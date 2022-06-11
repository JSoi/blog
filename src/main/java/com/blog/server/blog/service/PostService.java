package com.blog.server.blog.service;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addNewPost(PostDto.NewPost postDto) {
        //User 설정해주기
        User targetUser = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));
        postRepository.save(Post.builder()
                .user(targetUser)
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .image_url(postDto.getImage_url()).build());
    }

    @Transactional
    public void update(Long postId, PostDto.UpdatePost updatePost) {
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostID가 존재하지 않습니다."));
        targetPost.update(updatePost);
        postRepository.save(targetPost);
    }
}
