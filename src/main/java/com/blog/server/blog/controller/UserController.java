package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import com.blog.server.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public User userInfo(@AuthenticationPrincipal User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("UserID가 존재하지 않습니다."));
    }
}
