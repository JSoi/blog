package com.blog.server.blog.service;

import com.blog.server.blog.controller.PostController;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    ImageService imageService;

    @InjectMocks
    PostService postService;

    @Nested
    class Add {

        @Test
        void 이미지있는_포스트추가_성공() throws IOException {
            //given
            ClassPathResource resource = new ClassPathResource("static/img/sample.png");
            MultipartFile mockMultipartFile = new MockMultipartFile("sample.png", resource.getInputStream());
            PostController.PostForm postForm = PostController.PostForm.builder().title("title")
                    .content("content")
                    .image(mockMultipartFile).build();
            User user = User.builder().email("useremail@email.com").password("userpassword").nickname("usernickname")
                    .roles(Collections.singletonList("ROLE_USER")).build();
            when(imageService.uploadImage(postForm.getImage())).thenReturn("imgUrl");
            //when
            Post post = new Post(postForm, "imgUrl", user);
            postService.addNewPost(postForm, user);
            //then
            verify(postRepository, atLeastOnce()).save(any());
        }

        @Test
        void 이미지없는_포스트추가_성공() {
            //given
            PostController.PostForm postForm = PostController.PostForm.builder().title("title")
                    .content("content")
                    .image(null).build();
            User user = User.builder().email("useremail@email.com").password("userpassword").nickname("usernickname")
                    .roles(Collections.singletonList("ROLE_USER")).build();
            //when
            postService.addNewPost(postForm, user);
            //then
            verify(imageService, never()).uploadImage(any());
            verify(postRepository, atLeastOnce()).save(any());
        }

        @Nested
        class Fail {
            @Test
            void 제목없는포스트_추가_실패() {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                PostController.PostForm postForm = PostController.PostForm.builder().content("content").image(null).build();
                Set<ConstraintViolation<PostController.PostForm>> constraintViolations = validator.validate(postForm);
                assertThat(constraintViolations.size()).isEqualTo(1);
                assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("제목에 값을 입력하세요");
            }

            @Test
            void 내용없는포스트_추가_실패() {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                PostController.PostForm postForm = PostController.PostForm.builder().title("title").image(null).build();
                Set<ConstraintViolation<PostController.PostForm>> constraintViolations = validator.validate(postForm);
                assertThat(constraintViolations.size()).isEqualTo(1);
                assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("내용에 값을 입력하세요");
            }

        }
    }

    @Nested
    @DisplayName("업데이트")
    class Update {
        @Nested
        @DisplayName("성공")
        class Success {
            @Test
            void 업데이트_성공() {
                //given
                Long successId = 1L;
                Post post = Post.builder().id(1L).content("content").imageUrl("url").build();


                //when

                //then
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
            @Test
            void 업데이트_성공() {

            }
        }
    }
}