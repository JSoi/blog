package com.blog.server.blog.dto;

import lombok.*;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class NewPost {
        private Long user_id;
        private String title;
        private String content;
        private String image_url;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class UpdatePost {
        private String title;
        private String content;
        private String image_url;
    }
}
