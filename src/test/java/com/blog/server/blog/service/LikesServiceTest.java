package com.blog.server.blog.service;

import com.blog.server.blog.domain.Likes;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikesServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    LikesRepository likesRepository;
    @InjectMocks
    LikesService likesService;

    @Nested
    @DisplayName("성공")
    class Success {
        @Test
        @DisplayName("좋아요")
        void like() {
            //given
            Post post = Post.builder().id(1L).title("title").content("content").imageUrl("url").templates(1L).build();
            User user = User.builder().email("useremail@email.com").password("userpassword").nickname("usernickname")
                    .roles(Collections.singletonList("ROLE_USER")).build();

            Likes likes = Likes.builder().post(post).user(user).build();
            LikesDto likesDto = LikesDto.builder().post_id(1L).user(user).build();
            when(postRepository.findById(1L)).thenReturn(Optional.of(post));
            when(likesRepository.findByPostAndUser(post, user)).thenReturn(Optional.of(likes));
            //when
            likesService.doLike(likesDto);
            //then
            verify(likesRepository,times(1)).delete(likes);
            verify(postRepository, times(1)).updateLikeCount(post.getId(), -1L);
        }
        @Test
        @DisplayName("좋아요 취소")
        void unlike() {
            Post post = Post.builder().id(1L).title("title").content("content").imageUrl("url").templates(1L).build();
            User user = User.builder().email("useremail@email.com").password("userpassword").nickname("usernickname")
                    .roles(Collections.singletonList("ROLE_USER")).build();

            LikesDto likesDto = LikesDto.builder().post_id(1L).user(user).build();
            when(postRepository.findById(1L)).thenReturn(Optional.of(post));
            when(likesRepository.findByPostAndUser(post, user)).thenReturn(Optional.empty());
            //when
            likesService.doLike(likesDto);
            //then
            verify(likesRepository,times(1)).save(any());
            verify(postRepository, times(1)).updateLikeCount(post.getId(), 1L);
        }
    }

    @Nested
    @DisplayName("실패")
    class Fail {
        @Test
        void 포스트_ID_오류() {
            //given
            Post post = Post.builder().id(1L).title("title").content("content").imageUrl("url").templates(1L).build();
            User user = User.builder().email("useremail@email.com").password("userpassword").nickname("usernickname")
                    .roles(Collections.singletonList("ROLE_USER")).build();
            LikesDto likesDto = LikesDto.builder().post_id(1L).user(user).build();
            when(postRepository.findById(1L)).thenReturn(Optional.empty());

            //when&then
            assertThrows(BlogException.class,()->{
                likesService.doLike(likesDto);
            });
        }
    }
}