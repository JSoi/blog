package com.blog.server.blog.controller;

import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.CommentDto;
import com.blog.server.blog.repository.CommentRepository;
import com.blog.server.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping
    public Response.Simple addComments(@RequestBody CommentDto.NewComment commentDto) {
        commentService.addComment(commentDto);
        return Response.Simple.builder().result(true).code(200).build();
    }

    @DeleteMapping("{commentId}")
    public Response.Simple deleteComments(@PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
        return Response.Simple.builder().result(true).code(200).build();
    }

    @PutMapping("{commentId}")
    public Response.Simple fixComments(@PathVariable Long commentId, @RequestBody CommentDto.UpdateComment commentDto) {
        commentService.update(commentId, commentDto);
        return Response.Simple.builder().result(true).code(200).build();
    }
}
