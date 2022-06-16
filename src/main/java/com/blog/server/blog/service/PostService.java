package com.blog.server.blog.service;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.PostForm;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
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
    public void addNewPost(PostForm postForm, User user) {
        String imageUrl = postForm.getImage() == null ? null : imageService.uploadImage(postForm.getImage());
        postRepository.save(new Post(postForm, imageUrl, user));
    }

    @Transactional
    public void update(Long postId, PostDto.UpdatePost updatePost, Long userId) {
        // userId는 쓰지 않지만 추후에 확장 가능할 것 같다!
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(ErrorCode.POST_NOT_EXIST));
        targetPost.update(updatePost);
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
