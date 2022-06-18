package com.blog.server.blog.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class NewPost {
        private Long userId;
        private MultipartFile image;
        @NotBlank(message = "제목을 입력하세요")
        @Size(max = 25, message = "제목은 최대 25자입니다")
        private String title;
        @NotBlank(message = "내용을 입력하세요")
        @Size(max = 3000, message = "내용은 3000자 이하로 작성해주세요")
        private String content;
        private Long template;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class UpdatePost {
        @NotBlank(message = "제목을 입력하세요")
        @Size(max = 25, message = "제목은 최대 25자입니다")
        private String title;
        @NotBlank(message = "내용을 입력하세요")
        @Size(max = 3000, message = "내용은 3000자 이하로 작성해주세요")
        private String content;
        private MultipartFile image;
    }
}
