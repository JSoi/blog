package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<PostResponse> getAllPost() { // 고치기
        List<Post> targetPostList = postRepository.findAll();
        return targetPostList.stream().map(PostResponse::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/api/posts")
    public Response.Simple addPosts(@RequestBody PostDto.NewPost post, @AuthenticationPrincipal User user) {
        postService.addNewPost(post, user);
        return Response.Simple.builder().result(true).build();
    }

    /**
     * @return response
     * 추후에 추가할 예정
     */
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    @PostMapping("/api/image")
//    public Response.Simple addImage(@RequestParam("images") MultipartFile multipartFile, @AuthenticationPrincipal User user) {
//        try {
//            S3Uploader.uploadFiles(multipartFile, "static");
//        } catch (Exception e) {
//            return Response.Simple.builder().result(false).build();
//        }
//        return Response.Simple.builder().result(true).build();
//
//    }

    // 게시글 조회
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/api/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        return new PostResponse(targetPost);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/api/posts/{postId}")
    public Response.Simple deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        // 여기에 자기 포스트가 아니면 삭제할 수 없는 기능을 추가하면 좋을 것 같다!
        postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        postRepository.deleteById(postId);
        return Response.Simple.builder().result(true).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/api/posts/{postId}")
    public Response.Simple fixPost(@PathVariable Long postId, @RequestBody PostDto.UpdatePost requestDto, @AuthenticationPrincipal User user) {
        // 여기에 자기 포스트가 아니면 삭제할 수 없는 기능을 추가하면 좋을 것 같다!
        postService.update(postId, requestDto, user.getId());
        return Response.Simple.builder().result(true).build();
    }

    @Data
    @AllArgsConstructor
    static class PostResponse {
        private String nickname;
        private Long post_id; // query 해야될듯 ^^;
        private String title;
        private String content;
        private String image_url;
        private LocalDateTime created_at;
        private LocalDateTime modified_at;
        private String template;
        private List<Comment> comment;

        public PostResponse(Post post) {
            this.nickname = post.getUser().getNickname();
            this.title = post.getTitle();
            this.post_id = post.getId();
            this.content = post.getContent();
            this.image_url = post.getImage_url();
            this.created_at = post.getCreatedAt();
            this.modified_at = post.getModifiedAt();
            this.template = post.getTemplates();
            this.comment = post.getCommentList();
        }
    }

    @Data
    @AllArgsConstructor
    static class CommentResponse {
        private Long comment_id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CommentResponse(Comment comment) {
            this.comment_id = comment.getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();

        }
    }

}
