package com.blog.server.blog.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.util.List;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void addNewPost(PostDto.NewPost postDto, User user) {
        postDto.setUser_id(user.getId());
        User targetUser = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new BlogException(ErrorCode.USER_NOT_EXIST));
        postRepository.save(Post.builder()
                .user(targetUser)
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .imageUrl(postDto.getImage_url()).build());
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
