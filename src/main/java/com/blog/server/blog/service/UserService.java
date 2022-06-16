package com.blog.server.blog.service;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
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
        if(userRepository.existsUserByEmail(userRegister.getEmail())){
            throw new BlogException(ErrorCode.EXIST_EMAIL);
        }
        Validator.validateRegisterUser(userRegister.getNickname(), userRegister.getPassword());
        User newUser = User.builder().name(userRegister.getName())
                .nickname(userRegister.getNickname())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .introduce(userRegister.getIntroduce())
                .roles(Collections.singletonList("ROLE_USER"))
                .profileImageUrl(userRegister.getProfileImageUrl())
                .build();

        userRepository.save(newUser);
        return Response.Simple.builder().result(true).build();
    }

    @Transactional
    public Response.Login login(UserDto.Login loginDto) {
        User targetUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()
                -> new BlogException(ErrorCode.BAD_LOGIN));
        if (!passwordEncoder.matches(loginDto.getPassword(), targetUser.getPassword())) {
            throw new BlogException(ErrorCode.BAD_LOGIN);
        }
        String token = jwtTokenProvider.createToken(targetUser.getEmail(), targetUser.getRoles());
        return Response.Login.builder().result(true).userToken(token).nickname(targetUser.getNickname()).build();
    }
}
