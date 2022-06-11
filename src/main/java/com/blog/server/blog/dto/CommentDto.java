package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class NewComment {
        Long user_id;
        Long post_id;
        String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class UpdateComment {
        Long user_id;
        Long post_id;
        Long comment_id;
        String content;
    }
}
