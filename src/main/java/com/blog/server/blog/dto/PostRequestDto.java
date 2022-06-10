package com.blog.server.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private Long user_id;
    private String title;
    private String content;
    private String image_url;
}
