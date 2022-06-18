package com.blog.server.blog.controller;


import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.service.LikesService;
import com.blog.server.blog.validaton.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseStatus(HttpStatus.ACCEPTED)
public class LikesController {
    private final LikesService likeService;

    @RequestMapping(value = "/{postId}/like", method = {RequestMethod.POST,RequestMethod.DELETE})
    public Response.Simple doLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN_TO_LIKE);
        LikesDto targetLikes = LikesDto.builder().postId(postId).user(user).build();
        likeService.doLike(targetLikes);
        return Response.Simple.builder().build();
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    @DeleteMapping("/{postId}/like")
//    public Response.Simple unLike(@PathVariable Long postId, @AuthenticationPrincipal User user) {
//        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
//        LikesDto targetLikes = LikesDto.builder().post_id(postId).user_id(user.getId()).build();
//        return likeService.undoLike(targetLikes);
//
//    }
}
