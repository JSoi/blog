package com.blog.server.blog.service;

import com.blog.server.blog.domain.Likes;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.blog.server.blog.excpetion.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void doLike(LikesDto likesDto) {
        Post targetPost = postRepository.findById(likesDto.getPostId()).orElseThrow(()
                -> new BlogException(POST_NOT_EXIST));

        Likes targetLikes = likesRepository.findByPostAndUser(targetPost, likesDto.getUser()).orElse(null);
        if (targetLikes != null) {
            postRepository.updateLikeCount(targetPost.getId(), -1L);
            likesRepository.delete(targetLikes);
        } else {
            postRepository.updateLikeCount(targetPost.getId(), 1L);
            likesRepository.save(new Likes(targetPost, likesDto.getUser()));
        }
    }

    // 쓰지 않지만 API에 명시됨
//    @Transactional
//    public Response.Simple undoLike(LikesDto likesDto) {
//        User targetUser = userRepository.findById(likesDto.getUser_id()).orElseThrow(()
//                -> new BlogException(USER_NOT_EXIST));
//
//        Likes targetLikes = likesRepository.findByUser(targetUser).orElseThrow(()
//                -> new BlogException(LIKES_NOT_EXIST));
//
//        likesRepository.delete(targetLikes);
//
//        return Response.Simple.builder().result(true).build();
//    }
}
