package com.blog.server.blog.controller;


import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.LikesDto;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikesController {
    private final LikesRepository likesRepository;
    private final LikesService likeService;

    @PostMapping("/{postId}/like")
    public Response.Simple addLike(@PathVariable Long postId, @RequestBody LikesDto likesDto){
        return likeService.dolike(postId,likesDto);
    }

    @DeleteMapping("/{postId}/like")
    public Response.Simple unLike(@PathVariable Long postId, @RequestBody LikesDto likesDto) {
            likesRepository.findById(likesDto.getPost_id()).orElseThrow(() -> new IllegalArgumentException("INVALID LIKEID"));
            likesRepository.deleteById(postId);
            return Response.Simple.builder().result(true).build();
    }
}
