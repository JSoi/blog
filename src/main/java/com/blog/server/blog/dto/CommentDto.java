package com.blog.server.blog.dto;

import lombok.*;

public class CommentDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class NewComment {
        Long user_id;
        Long post_id;
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
        String content;
    }
}
