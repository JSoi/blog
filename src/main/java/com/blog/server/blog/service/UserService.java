package com.blog.server.blog.service;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import com.blog.server.blog.validaton.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Response.Simple register(UserDto.Register userRegister) {
        Validator.validateRegisterUser(userRegister.getNickname(), userRegister.getPassword());
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

    @Transactional
    public Response.Login login(UserDto.Login loginDto) {
        User targetUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        if (!passwordEncoder.matches(loginDto.getPassword(), targetUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(targetUser.getEmail(), targetUser.getId(), targetUser.getRoles());
        return Response.Login.builder().result(true).token(token).nickname(targetUser.getNickname()).build();
    }
}
