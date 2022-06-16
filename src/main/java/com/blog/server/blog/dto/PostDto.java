package com.blog.server.blog.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class NewPost {
        @NotNull
        private MultipartFile image;
        private Long userId;
        @NotBlank(message = "제목에 빈 칸을 입력하지 마세요")
        private String title;
        @NotBlank(message = "내용에 빈 칸을 입력하지 마세요")
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class UpdatePost {
        @NotBlank(message = "제목에 빈 칸을 입력하지 마세요")
        private String title;
        @NotBlank(message = "내용에 빈 칸을 입력하지 마세요")
        private String content;
        private String imageUrl;
    }
}
