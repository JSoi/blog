package com.blog.server.blog.controller;


import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikesController {
    private final LikesService likeService;

    @PostMapping("/{postId}/like")
    public Response.Simple doLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        if (user == null) {
            throw new BlogException(ErrorCode.NEED_LOGIN_TO_LIKE);
        }
        return likeService.doLike(LikesDto.builder().post_id(postId).user_id(user.getId()).build());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{postId}/like")
    public Response.Simple unLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        return likeService.undoLike(LikesDto.builder().post_id(postId).user_id(user.getId()).build());

    }
}
