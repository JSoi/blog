package com.blog.server.blog.service;

import com.blog.server.blog.controller.PostController;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
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

import javax.swing.text.html.Option;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                    .introduce("introduce").nickname("nickname").name("name").build();
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
            User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                    .introduce("introduce").nickname("nickname").name("name").build();
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
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); // 여기에 쓰는 게 맞나 싶다 ^^;
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
            void 업데이트_성공() throws IOException {
                //given
                Long successId = 1L;
                Post post = Post.builder().id(1L).title("title").content("content").imageUrl("url").templates(1L).build();
                when(postRepository.findById(any())).thenReturn(Optional.of(post));
                when(imageService.uploadImage(any())).thenReturn("checkString");
                //when
                MockMultipartFile mockMultipartFile = new MockMultipartFile("sample.png", new ClassPathResource("static/img/sample.png").getInputStream());
                PostController.PostForm newForm = PostController.PostForm.builder().title("제목").content("내용")
                        .image(mockMultipartFile).template(2L).build();
                User user = User.builder().nickname("testnick").email("testemail@email.com").
                        name("testname").password("testpassword").roles(Collections.singletonList("ROLE_USER")).build();
                postService.update(successId, newForm, user);
                //then
                verify(imageService, times(1)).deleteImage(anyString());
                verify(imageService, times(1)).uploadImage(any());

                assertThat(post.getContent()).isEqualTo(newForm.getContent());
                assertThat(post.getTitle()).isEqualTo(newForm.getTitle());

                verify(postRepository, times(1)).save(post);
            }

            @Test
            void 기존사진_지우기_업데이트성공() {
                //given
                Long successId = 1L;
                Post post = Post.builder().id(1L).title("title").content("content").imageUrl("url").templates(1L).build();
                when(postRepository.findById(any())).thenReturn(Optional.of(post));
                //when
                PostController.PostForm newForm = PostController.PostForm.builder().title("제목").content("내용")
                        .template(2L).image(null).build();
                User user = User.builder().nickname("testnick").email("testemail@email.com").
                        name("testname").password("testpassword").roles(Collections.singletonList("ROLE_USER")).build();
                postService.update(successId, newForm, user);
                //then
                verify(imageService, times(1)).deleteImage(anyString());
                verify(imageService, never()).uploadImage(any());

                assertThat(post.getContent()).isEqualTo(newForm.getContent());
                assertThat(post.getTitle()).isEqualTo(newForm.getTitle());

                verify(postRepository, times(1)).save(post);
            }

            @Test
            void 사진새로_추가_업데이트성공() throws IOException {
                //given
                Long successId = 1L;
                Post post = Post.builder().id(successId).title("title").content("content").templates(1L).build();
                User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                        .introduce("introduce").nickname("nickname").name("name").build();
                MockMultipartFile mockMultipartFile = new MockMultipartFile("sample.png", new ClassPathResource("static/img/sample.png").getInputStream());
                PostController.PostForm newForm = PostController.PostForm.builder().title("제목").content("내용")
                        .image(mockMultipartFile).template(2L).build();

                when(postRepository.findById(any())).thenReturn(Optional.of(post));
                when(imageService.uploadImage(any())).thenReturn("checkString");

                //when

                postService.update(successId, newForm, user);

                //then
                verify(imageService, never()).deleteImage(anyString());
                verify(imageService, times(1)).uploadImage(any());

                assertThat(post.getContent()).isEqualTo(newForm.getContent());
                assertThat(post.getTitle()).isEqualTo(newForm.getTitle());

                verify(postRepository, times(1)).save(post);
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
            @Test
            void 없는_포스트ID() throws IOException {
                //giuen
                Long successId = 1L;
                User user = User.builder().email("testemail@email.com").password("testpassword").roles(Collections.singletonList("ROLE_USER"))
                        .introduce("introduce").nickname("nickname").name("name").build();
                MockMultipartFile mockMultipartFile = new MockMultipartFile("sample.png", new ClassPathResource("static/img/sample.png").getInputStream());
                PostController.PostForm newForm = PostController.PostForm.builder().title("제목").content("내용")
                        .image(mockMultipartFile).template(2L).build();

                when(postRepository.findById(any())).thenReturn(Optional.empty());
                //when, then
                BlogException blogException = assertThrows(BlogException.class, () -> {
                            postService.update(successId, newForm, user);
                        }
                );
                assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_EXIST);
            }


        }
        @Nested
        class PlusView{
            @Test
            void 뷰_증가_성공(){
                //given
                when(postRepository.existsById(any())).thenReturn(true);
                //when
                postService.plusView(1L);
                //then
                verify(postRepository,times(1)).updateView(1L);
            }
            @Test
            void 뷰_증가_실패(){
                when(postRepository.existsById(any())).thenReturn(false);
                //when&then
                BlogException blogException = assertThrows(BlogException.class,()->{
                    postService.plusView(1L);
                });
                assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_EXIST);
            }
        }
    }
}