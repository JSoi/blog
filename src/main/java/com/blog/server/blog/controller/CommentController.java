package com.blog.server.blog.controller;

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
    public void addComments(@RequestBody CommentDto commentDto){
        commentService.addComment(commentDto);

    }

    @DeleteMapping("{commentId}")
    public void deleteComments(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
    }

    @PutMapping("{commentId}")
    public void fixComments(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        commentService.update(commentId, commentDto);
    }
}
