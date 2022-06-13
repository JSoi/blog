package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import com.blog.server.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<Post> getAllPost() { // 고치기
        return postRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/api/posts")
    public Response.Simple addPosts(@RequestBody PostDto.NewPost post, @AuthenticationPrincipal User user) {
        post.setUser_id(user.getId());
        postService.addNewPost(post);
        return Response.Simple.builder().result(true).build();
    }

    /**
     * @return response
     * 추후에 추가할 예정
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/api/image")
    public Response.Simple addImage() {
        return Response.Simple.builder().result(true).build();
    }

    // 게시글 조회

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
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

}
