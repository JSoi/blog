package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated
public class UserDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Register {
        @Length(min = 1, max = 10, message = "이름은 1자 이상, 10자 이하로 설정해주세요")
        @NotBlank(message = "이름을 입력하세요")
        private String name;

        @Length(min = 3, max = 20, message = "별명은 3자 이상, 20자 이하로 설정해주세요")
        @NotBlank(message = "별명을 입력하세요")
        @Pattern(regexp="^[a-zA-Z0-9]*$",message = "별명을 영어와 숫자로만 구성해주세요")
        private String nickname;

        @Email(message = "올바른 이메일 형식을 입력해 주세요")
        @NotBlank(message = "아이디를 입력하세요")
        @Length( max = 50, message = "아이디는 50자 이하로 설정해주세요")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요")
        @Length( min=4, max = 25, message = "비밀번호는 4자 이상 25자 이하로 설정해주세요")
        private String password;

        @Length(max=1000, message = "자기소개를 1000자 이하로 작성해주세요")
        private String introduce;

        @Length(max=255, message = "이미지 파일명이 너무 깁니다")
        private String profileImageUrl;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Login{
        @NotBlank(message = "아이디를 입력하세요")
        @Size(max=50, message="아이디가 너무 깁니다")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요")
        @Size(max=25, message="비밀번호가 너무 깁니다")
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder    
    @Getter
    public static class Info{
        Long userId;
    }


}
