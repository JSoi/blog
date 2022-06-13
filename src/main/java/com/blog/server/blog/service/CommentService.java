package com.blog.server.blog.service;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.CommentDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.CommentRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addComment(CommentDto.NewComment newCommentDto) {
        Post targetPost = postRepository.findById(newCommentDto.getPost_id()).orElseThrow(() -> new BlogException(ErrorCode.POST_NOT_EXIST));
        User user = userRepository.findById(newCommentDto.getUser_id()).orElseThrow(() -> new BlogException(ErrorCode.USER_NOT_EXIST));
        commentRepository.save(new Comment(targetPost, user, newCommentDto.getContent()));

    }

    @Transactional
    public void updateComment(Long commentId, CommentDto.UpdateComment commentDto) {
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(() -> new BlogException(ErrorCode.COMMENT_NOT_EXIST));
        targetComment.update(commentDto);
        commentRepository.save(targetComment);
    }
}
