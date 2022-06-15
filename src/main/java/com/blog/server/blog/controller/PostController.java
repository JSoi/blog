package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.service.PostService;
import com.blog.server.blog.validaton.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<PostResponse> getAllPost() { // 고치기
        List<Post> targetPostList = postRepository.findAllByOrderByLikeCountDesc();
        List<PostResponse> result = new ArrayList<>();
        targetPostList.forEach(p -> result.add(new PostResponse(p)));
        return result;
    }

    @PostMapping("/api/posts")
    public Response.Simple addPosts(@Valid @RequestBody PostDto.NewPost post, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.addNewPost(post, user);
        return Response.Simple.builder().build();
    }


    // 게시글 조회
    @GetMapping("/api/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        postService.plusView(postId); // 조회 시 View가 하나 올라가는 함수
        return new PostResponse(targetPost);
    }


    @DeleteMapping("/api/posts/{postId}")
    public Response.Simple deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        postRepository.deleteById(postId);
        return Response.Simple.builder().build();
    }

    @PutMapping("/api/posts/{postId}")
    public Response.Simple fixPost(@PathVariable Long postId, @Valid @RequestBody PostDto.UpdatePost requestDto,
                                   @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.update(postId, requestDto, user.getId());
        return Response.Simple.builder().build();
    }

    @Data
    @AllArgsConstructor
    static class PostResponse {
        private String nickname, template, image_url, content, title;
        private Long post_id, like_count,view_count; // query 해야될듯 ^^;
        private LocalDateTime created_at, modified_at;
        private List<CommentResponse> comment;


        public PostResponse(Post post) {
            this.nickname = post.getUser().getNickname();
            this.title = post.getTitle();
            this.post_id = post.getId();
            this.content = post.getContent();
            this.image_url = post.getImage_url();
            this.created_at = post.getCreatedAt();
            this.modified_at = post.getModifiedAt();
            this.template = post.getTemplates();
            this.view_count = post.getViewCount();
            this.comment = post.getCommentList().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.like_count = (long) post.getLikesList().size();
        }
    }

    @Data
    @AllArgsConstructor
    public static class CommentResponse {
        private Long comment_id;
        private String content;
        private LocalDateTime createdAt, modifiedAt;

        public CommentResponse(Comment comment) {
            this.comment_id = comment.getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();

        }
    }

}
