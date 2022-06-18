package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostFormDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.service.PostService;
import com.blog.server.blog.validaton.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseStatus(HttpStatus.ACCEPTED)
public class PostController {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<PostResponse> getAllPost(@AuthenticationPrincipal User user) { // 고치기
        List<Post> targetPostList = postRepository.findAllByOrderByLikeCountDesc();
        List<PostResponse> result = new ArrayList<>();
        for (Post p : targetPostList) {
            result.add(processEachPost(user, p));
        }
        return result;
    }
    // 게시글 조회

    @GetMapping("/api/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);

        postService.plusView(postId); // 조회 시 View가 하나 올라가는 함수
        Post post = postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        return processEachPost(user, post);
    }

    private PostResponse processEachPost(User user, Post p) {
        boolean likeByMe = user != null && likesRepository.existsLikesByPostAndUser(p, user);
        String nickName = p.getUser().getNickname();
        return new PostResponse(p, likeByMe, nickName);
    }


    @PostMapping(value = "/api/posts", consumes = {"multipart/form-data"})
    public Response.Simple addPosts(@Valid @ModelAttribute PostFormDto postFormDto, BindingResult bindingResult,
                                    @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.addNewPost(postFormDto, user);
        return Response.Simple.builder().build();
    }


    @DeleteMapping("/api/posts/{postId}")
    public Response.Simple deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        postRepository.deleteById(postId);
        return Response.Simple.builder().build();
    }

    @PutMapping(value = "/api/posts/{postId}", consumes = {"multipart/form-data"})
    public Response.Simple fixPost(@PathVariable Long postId, @Valid @ModelAttribute PostFormDto requestDto,
                                   @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.update(postId, requestDto, user);
        return Response.Simple.builder().build();
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Data
    @AllArgsConstructor
    static class PostResponse {

        private Long id, likeCount, viewCount, template; // query 해야될듯 ^^;
        private String nickname, imageUrl, content, title;
        private LocalDateTime createdAt, modifiedAt;

        private List<CommentResponse> comment;
        ////추가하기
        private boolean likeByMe;


        @Builder
        public PostResponse(Post post, boolean like, String nickname) {
            this.title = post.getTitle();
            this.id = post.getId();
            this.content = post.getContent();
            this.imageUrl = post.getImageUrl();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();
            this.template = post.getTemplates();
            this.viewCount = post.getViewCount();
            this.comment = post.getCommentList().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.likeCount = (long) post.getLikesList().size();
            this.likeByMe = like;
            this.nickname = nickname;
        }
    }

    @Data
    @AllArgsConstructor
    public static class CommentResponse {
        private Long id;
        private String content;
        private LocalDateTime createdAt, modifiedAt;

        public CommentResponse(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();

        }
    }
}
