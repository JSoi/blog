package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import com.blog.server.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("/api/posts")
    public List<Post> getAllPost() { // 고치기
        return postRepository.findAll();
    }

    @PostMapping("/api/posts")
    public Response.Simple addPosts(@RequestBody PostDto.NewPost post, @RequestHeader HttpHeaders header) {
        // post에서 token을 번역해서 id를 가져오는 일이 필요하다
        // 이는 JwtTokenProvider.getUserPk를 통해 가져오는 게 좋을 것 같다.
        log.info("VALUE : {}",jwtTokenProvider.getUserPk(header.getFirst("X-AUTH-TOKEN")));
        postService.addNewPost(post);
        return Response.Simple.builder().result(true).build();
    }

    /**
     * @return response
     * 추후에 추가할 예정
     */
    @PostMapping("/api/image")
    public Response.Simple addImage() {
        return Response.Simple.builder().result(true).build();
    }

    // 게시글 조회
    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return  postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostID가 존재하지 않습니다."));
    }

    // delete GET 방식 얘기하기
    @DeleteMapping("/api/posts/{postId}")
    public Response.Simple deletePost(@PathVariable Long postId) {
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostID가 존재하지 않습니다."));
        postRepository.deleteById(postId);
        return Response.Simple.builder().result(true).build();
    }

    @PutMapping("/api/posts/{postId}")
    public Response.Simple fixPost(@PathVariable Long postId, @RequestBody PostDto.UpdatePost requestDto) {
        postService.update(postId, requestDto);
        return Response.Simple.builder().result(true).build();
    }


}
