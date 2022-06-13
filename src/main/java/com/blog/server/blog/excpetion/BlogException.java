package com.blog.server.blog.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogException extends RuntimeException {
    private final ErrorCode errorCode;
}
