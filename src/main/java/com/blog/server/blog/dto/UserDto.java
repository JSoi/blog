package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Validated
public class UserDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Register {
        private String name;
        private String nickname;
        @Email(message = "올바른 이메일 형식을 입력해 주세요")
        @NotBlank(message = "아이디에 빈 칸을 입력하지 마세요")
        private String email;
        @NotBlank(message = "비밀번호에 빈 칸을 입력하지 마세요")
        private String password;
        private String introduce;
        private String profile_image_url;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Login{
        @NotBlank(message = "아이디에 빈 칸을 입력하지 마세요")
        private String email;
        @NotBlank(message = "비밀번호에 빈 칸을 입력하지 마세요")
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
