package com.blog.server.blog.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseSimple {
    @Builder.Default
    boolean result = false;
    @Builder.Default
    int code = -1;
}