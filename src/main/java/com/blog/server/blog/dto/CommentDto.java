package com.blog.server.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    Long user_id;
    Long post_id;
    String content;
}
