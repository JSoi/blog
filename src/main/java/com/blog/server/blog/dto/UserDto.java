package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        @Size(min = 1, max = 10, message = "이름은 1자 이상, 10자 이하로 설정해주세요")
        private String name;
        @Size(min = 1, max = 20, message = "별명은 1자 이상, 20자 이하로 설정해주세요")
        @Pattern(regexp="^[a-zA-Z0-9]*$",message = "별명을 영어와 숫자로만 구성해주세요")
        private String nickname;
        @Email(message = "올바른 이메일 형식을 입력해 주세요")
        @NotBlank(message = "아이디에 값을 입력하세요")
        @Size( max = 20, message = "아이디는 20자 이하로 설정해주세요")
        private String email;
        @NotBlank(message = "비밀번호에 값을 입력하세요")
        private String password;
        private String introduce;
        private String profile_image_url;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Login{
        @NotBlank(message = "아이디에 값을 입력하세요")
        private String email;
        @NotBlank(message = "비밀번호에 값을 입력하세요")
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
