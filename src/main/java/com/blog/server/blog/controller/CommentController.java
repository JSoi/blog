package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.CommentDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.CommentRepository;
import com.blog.server.blog.service.CommentService;
import com.blog.server.blog.validaton.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("{postId}")
    public Response.Simple addComments(@PathVariable Long postId, @Valid @RequestBody CommentDto.NewComment commentDto, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        commentService.addComment(postId, commentDto);
        return Response.Simple.builder().build();
    }

    @DeleteMapping("{commentId}")
    public Response.Simple deleteComments(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        commentRepository.deleteById(commentId);
        return Response.Simple.builder().build();
    }

    @PutMapping("{commentId}")
    public Response.Simple fixComments(@PathVariable Long commentId, @Valid @RequestBody CommentDto.UpdateComment commentDto,
                                       @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        commentService.updateComment(commentId, commentDto);
        return Response.Simple.builder().build();
    }
}
