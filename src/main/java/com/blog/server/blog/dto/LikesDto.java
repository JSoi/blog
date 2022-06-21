package com.blog.server.blog.dto;

import com.blog.server.blog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LikesDto {
    User user;
    Long post_id;
}
