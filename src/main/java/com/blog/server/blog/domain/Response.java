package com.blog.server.blog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
public class Response {
    @Builder.Default
    boolean result = false;
    @Builder.Default
    int code = -1;
}