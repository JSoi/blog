package com.blog.server.blog.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public class CommentDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class NewComment {
        Long user_id;
        @NotBlank(message = "댓글에 내용을 입력하세요")
        String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class DeleteComment {
        Long user_id;
        Long post_id;
        Long comment_id;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class UpdateComment {
        Long post_id;
        Long comment_id;
        @NotBlank(message = "댓글에 내용을 입력하세요")
        String content;
    }
}
