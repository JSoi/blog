package com.blog.server.blog.dto;

import lombok.*;


public class Response {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Simple{
        @Builder.Default
        boolean result = false;
        @Builder.Default
        int code = 100; // 실패 Default
    }
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login{
        boolean result;
        String token;
        String nickname;
    }

}