package com.blog.server.blog.service;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
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
    public void addNewPost(PostDto.NewPost postDto, User user) {
        postDto.setUser_id(user.getId());
        User targetUser = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new BlogException(ErrorCode.USER_NOT_EXIST));
        postRepository.save(Post.builder()
                .user(targetUser)
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .image_url(postDto.getImage_url()).build());
    }

    @Transactional
    public void update(Long postId, PostDto.UpdatePost updatePost, Long userId) {
        // userId는 쓰지 않지만 추후에 확장 가능할 것 같다!
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(ErrorCode.POST_NOT_EXIST));
        targetPost.update(updatePost);
        postRepository.save(targetPost);
    }


}
