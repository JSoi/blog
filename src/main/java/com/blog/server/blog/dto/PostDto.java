package com.blog.server.blog.dto;

import com.blog.server.blog.domain.Likes;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class NewPost {
        private Long user_id;
        @NotBlank(message = "제목에 빈 칸을 입력하지 마세요")
        private String title;
        @NotBlank(message = "내용에 빈 칸을 입력하지 마세요")
        private String content;
        private String image_url;
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
        private String image_url;
    }
}
