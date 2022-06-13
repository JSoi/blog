package com.blog.server.blog.controller;


import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikesController {
    private final LikesService likeService;

    @PostMapping("/{postId}/like")
    public Response.Simple doLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        return likeService.doLike(LikesDto.builder().post_id(postId).user_id(user.getId()).build());
    }

    @DeleteMapping("/{postId}/like")
    public Response.Simple unLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        return likeService.undoLike(LikesDto.builder().post_id(postId).user_id(user.getId()).build());

    }
}
