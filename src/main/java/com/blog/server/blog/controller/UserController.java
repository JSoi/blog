package com.blog.server.blog.controller;

import com.blog.server.blog.domain.response.ResponseSimple;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.LoginDto;
import com.blog.server.blog.dto.UserRequestDto;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    //회원등록
    @PostMapping("/register")
    public ResponseSimple register(@RequestBody User user) {
        userRepository.save(user);
        return ResponseSimple.builder().result(true).build();
    }

    //로그인
    @PostMapping("/login")
    public ResponseSimple login(@RequestBody LoginDto loginDto) {
        if (userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).isPresent()) {
            return ResponseSimple.builder().result(true).build();
        } else {
            return ResponseSimple.builder().result(false).build();
        }
    }

    //회원정보조회 - Token으로 치환 필요
    @GetMapping("/user")
    public User userInfo(@RequestBody UserRequestDto userRequestDto) {
        return userRepository.findById(userRequestDto.getUser_id()).orElseThrow(() -> new IllegalArgumentException("UserID가 존재하지 않습니다."));
    }
}
