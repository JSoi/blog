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
    public void addComment(CommentDto newCommentDto,  Long postId, User user) {
        Post targetPost = postRepository.findById(postId).orElseThrow(()
                -> new BlogException(ErrorCode.POST_NOT_EXIST));

        commentRepository.save(new Comment(targetPost, user, newCommentDto));
    }

    @Transactional
    public void updateComment(Long commentId, CommentDto commentDto) {
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(()
                -> new BlogException(ErrorCode.COMMENT_NOT_EXIST));
        targetComment.update(commentDto);
        commentRepository.save(targetComment);
    }
}
