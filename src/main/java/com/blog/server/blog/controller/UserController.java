package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원등록
    @PostMapping("/register")
    public Response.Simple register(@RequestBody UserDto.Register userRegister, @AuthenticationPrincipal User user) {
        User newUser = User.builder().name(userRegister.getName())
                .nickname(userRegister.getNickname())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .introduce(userRegister.getIntroduce())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(newUser);
        return Response.Simple.builder().result(true).build();
    }

    //로그인
    @PostMapping("/login")
    public Response.Login login(@RequestBody UserDto.Login loginDto, @AuthenticationPrincipal User user) {
        User targetUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        if (!passwordEncoder.matches(loginDto.getPassword(), targetUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(targetUser.getEmail(), targetUser.getId(), targetUser.getRoles());
        return Response.Login.builder().result(true).token(token).nickname(targetUser.getNickname()).build();
    }

    @GetMapping("/user")
    public User userInfo(@AuthenticationPrincipal User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("UserID가 존재하지 않습니다."));
    }
    //회원정보조회 - Token으로 치환 필요
}
