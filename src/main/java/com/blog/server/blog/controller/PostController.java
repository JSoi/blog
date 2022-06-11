package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.response.ResponseSimple;
import com.blog.server.blog.dto.PostDto;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/api/posts")
    public ResponseSimple addPosts(@RequestBody PostDto.NewPost post) {
        postService.addNewPost(post);
        return ResponseSimple.builder().result(true).build();
    }

    /**
     * @return response
     * 추후에 추가할 예정
     */
    @PostMapping("/api/image")
    public ResponseSimple addImage() {
        return ResponseSimple.builder().result(true).build();
    }

    // 게시글 조회
    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return  postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostID가 존재하지 않습니다."));
    }

    // delete GET 방식 얘기하기
    @DeleteMapping("/api/posts/{postId}")
    public ResponseSimple deletePost(@PathVariable Long postId) {
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostID가 존재하지 않습니다."));
        postRepository.deleteById(postId);
        return ResponseSimple.builder().result(true).build();
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseSimple fixPost(@PathVariable Long postId, @RequestBody PostDto.UpdatePost requestDto) {
        postService.update(postId, requestDto);
        return ResponseSimple.builder().result(true).build();
    }


}
