package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.service.UserService;
import com.blog.server.blog.validaton.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@ResponseStatus(HttpStatus.ACCEPTED)
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    //회원등록
    @PostMapping("/register")
    public Response.Simple register(@Valid @RequestBody UserDto.Register userRegister, @AuthenticationPrincipal User user) {
        Validator.alreadyLoggedIn(user, ErrorCode.ALREADY_LOGGED_IN);
        return userService.register(userRegister);
    }

    //로그인
    @PostMapping("/login")
    public Response.Login login(@Valid @RequestBody UserDto.Login loginDto, @AuthenticationPrincipal User user) {
        Validator.alreadyLoggedIn(user, ErrorCode.ALREADY_LOGGED_IN);
        return userService.login(loginDto);
    }

    @GetMapping("/user")
    public UserInfo userInfo(@AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        return new UserInfo(user);
    }

    //ResponseUser 필요
    @Data
    @AllArgsConstructor
    static class UserInfo {
        private String name;
        private String nickname;
        private String email;
        private String introduce;
        private String profileImageUrl;
        private LocalDateTime createdAt;


        public UserInfo(User user) {
            this.name = user.getName();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.profileImageUrl = user.getProfileImageUrl();
            this.introduce = user.getIntroduce();
            this.createdAt = user.getCreatedAt();

        }
    }
}
