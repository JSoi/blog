package com.blog.server.blog.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 문제가 있을 때는 대부분 Exception에서 처리된다.
 * 그래서 성공하는 케이스를 Default로 설정하였다!
 * */

public class Response {
    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Simple {
        @Builder.Default
        boolean result = true;
        @Builder.Default
        int code = 200; // 성공 Default
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        String message;
        @Builder.Default
        int code = 200;
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        @Builder.Default
        boolean result = true;
        String token;
        String nickname;
    }


    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Image {
        @Builder.Default
        boolean result = true;
        @Builder.Default
        String imageUrl = null;
    }

}