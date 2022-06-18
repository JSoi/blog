package com.blog.server.blog.service;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.dto.PostFormDto;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;

    @Transactional
    public void addNewPost(PostFormDto postFormDto, User user) {
        String imageUrl = postFormDto.getImage() == null ? null : imageService.uploadImage(postFormDto.getImage());
        postRepository.save(new Post(postFormDto, imageUrl, user));
    }

    @Transactional
    public void update(Long postId, PostFormDto updatePostDto, User user) {
        // userId는 쓰지 않지만 추후에 확장 가능할 것 같다!
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(ErrorCode.POST_NOT_EXIST));
        if (targetPost.getImageUrl() != null) imageService.deleteImage(targetPost.getImageUrl());
        String updateImageUrl = updatePostDto.getImage() == null ? null : imageService.uploadImage(updatePostDto.getImage());
        targetPost.update(updatePostDto, updateImageUrl);

        postRepository.save(targetPost);
    }


    @Transactional
    public void plusView(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new BlogException(POST_NOT_EXIST);
        }
        postRepository.updateView(postId);
    }
}
