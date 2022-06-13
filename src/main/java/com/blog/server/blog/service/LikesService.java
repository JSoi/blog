package com.blog.server.blog.service;

import com.blog.server.blog.domain.Likes;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response.Simple doLike(LikesDto likesDto) {
        User targetUser = userRepository.findById(likesDto.getUser_id()).orElseThrow(() -> new IllegalArgumentException("INVALID USERID"));
        Post targetPost = postRepository.findById(likesDto.getPost_id()).orElseThrow(() -> new IllegalArgumentException("INVALID POSTID"));
        likesRepository.save(new Likes(targetPost,targetUser));
        return Response.Simple.builder().result(true).build();
    }

    @Transactional
    public Response.Simple undoLike(LikesDto likesDto) {
        User targetUser = userRepository.findById(likesDto.getUser_id()).orElseThrow(() -> new IllegalArgumentException(("INVALID USERID")));
        Likes targetLikes = likesRepository.findByUser(targetUser).orElseThrow(() -> new IllegalArgumentException("INVALID POSTID"));
        likesRepository.delete(targetLikes);
        return Response.Simple.builder().result(true).build();
    }
}
