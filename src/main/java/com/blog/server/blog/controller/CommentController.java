package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.CommentDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.repository.CommentRepository;
import com.blog.server.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping
    public Response.Simple addComments(@RequestBody CommentDto.NewComment commentDto, @AuthenticationPrincipal User user) {
        commentService.addComment(user.getId(),commentDto);
        return Response.Simple.builder().result(true).code(200).build();
    }

    @DeleteMapping("{commentId}")
    public Response.Simple deleteComments(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        // User에 따라 허용할 수 있는 로직 추가하면 좋을듯
        commentRepository.deleteById(commentId);
        return Response.Simple.builder().result(true).code(200).build();
    }

    @PutMapping("{commentId}")
    public Response.Simple fixComments(@PathVariable Long commentId, @RequestBody CommentDto.UpdateComment commentDto, @AuthenticationPrincipal User user) {
        // User에 따라 허용할 수 있는 로직 추가하면 좋을듯
        commentService.updateComment(commentId, commentDto);
        return Response.Simple.builder().result(true).code(200).build();
    }
}
