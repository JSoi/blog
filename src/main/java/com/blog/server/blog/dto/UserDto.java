package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Register {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String introduce;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Login{
        private String email;
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Info{
        Long user_id;
    }


}
