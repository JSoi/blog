package com.blog.server.blog.controller;


import com.blog.server.blog.domain.response.ResponseSimple;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikesController {
    private final LikesRepository likesRepository;
    private final LikesService likeService;

    @PostMapping("/{postId}/like")
    public ResponseSimple addLike(@RequestBody LikesDto likesDto){
        return likeService.dolike(likesDto);
    }

        @DeleteMapping("/{postId}/like")
    public ResponseSimple unLike(@PathVariable Long postId, @RequestBody LikesDto likesDto) {
            likesRepository.findById(likesDto.getPost_id()).orElseThrow(() -> new IllegalArgumentException("INVALID LIKEID"));
            likesRepository.deleteById(postId);
            return ResponseSimple.builder().result(true).build();
    }
}
