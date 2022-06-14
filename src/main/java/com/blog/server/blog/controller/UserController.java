package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.blog.server.blog.excpetion.ErrorCode.USER_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    //회원등록
    @PostMapping("/register")
    public Response.Simple register(@RequestBody UserDto.Register userRegister) {
        return userService.register(userRegister);
    }

    //로그인
    @PostMapping("/login")
    public Response.Login login(@RequestBody UserDto.Login loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/user")
    public UserInfo userInfo(@AuthenticationPrincipal User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new BlogException(USER_NOT_EXIST));
        return new UserInfo(findUser);
    }

    //ResponseUser 필요
    @Data
    @AllArgsConstructor
    static class UserInfo {
        private String name;
        private String nickname;
        private String email;
        private String introduce;
        private String profile_image_url;
        private LocalDateTime createdAt;


        public UserInfo(User user) {
            this.name = user.getName();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.profile_image_url = user.getProfile_image_url();
            this.introduce = user.getIntroduce();
            this.createdAt = user.getCreatedAt();

        }
    }
}
