package com.blog.server.blog.service;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.CommentDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.CommentRepository;
import com.blog.server.blog.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    CommentService commentService;

    @Nested
    @DisplayName("댓글 추가")
    class AddComment {
        @Test
        void 실패() {
            CommentDto.NewComment commentDto = CommentDto.NewComment.builder().content("newComment").build();
            Long postId = 1L;
            User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                    .introduce("introduce").nickname("nickname").name("name").build();
            Post post = Post.builder().id(postId).title("title").content("content").imageUrl("url").templates(1L).build();
            when(postRepository.findById(postId)).thenReturn(Optional.empty());
            //when & then
            BlogException blogException = assertThrows(BlogException.class, () -> {
                commentService.addComment(postId, user, commentDto);
            });
            assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_EXIST);


        }

        @Test
        void 성공() {
            CommentDto.NewComment commentDto = CommentDto.NewComment.builder().content("newComment").build();
            Long postId = 1L;
            User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                    .introduce("introduce").nickname("nickname").name("name").build();
            Post post = Post.builder().id(postId).title("title").content("content").imageUrl("url").templates(1L).build();
            when(postRepository.findById(postId)).thenReturn(Optional.of(post));
            //WHEN
            commentService.addComment(postId, user, commentDto);
            //then
            verify(commentRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class UpdateComment {
        @Test
        void 실패() {
            when(commentRepository.findById(any())).thenReturn(Optional.empty());
            CommentDto.UpdateComment commentDto = CommentDto.UpdateComment.builder().content("new").build();
            BlogException blogException = assertThrows(BlogException.class, () -> {
                commentService.updateComment(1L, commentDto);
            });
            assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_EXIST);
        }

        @Test
        void 성공() {
            Comment comment = Comment.builder().id(1L).content("original").build();
            when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
            // when
            CommentDto.UpdateComment commentDto = CommentDto.UpdateComment.builder().content("new").build();
            commentService.updateComment(1L, commentDto);
            // then
            verify(commentRepository, times(1)).save(comment);
            assertThat(comment.getContent()).isEqualTo("new");
        }
    }
}