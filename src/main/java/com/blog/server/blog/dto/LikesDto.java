package com.blog.server.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LikesDto {
    Long user_id;
    Long post_id;
}
