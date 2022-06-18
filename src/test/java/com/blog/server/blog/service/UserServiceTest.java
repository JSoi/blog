package com.blog.server.blog.service;

import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Nested
    class register_user {
        @BeforeEach

        @Test
        void 회원가입_성공() {

            UserDto.Register userRegisterDto = UserDto.Register.builder()
                    .name("testname").email("testemail@testemail.com")
                    .introduce("hitest").password("testsuccess")
                    .build();
            UserService userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider);
//            Assertions.assertThrows(BlogException.class,()->userService.register(userRegisterDto));
            Assertions.assertThat(userService.register(userRegisterDto).getCode());
        }

        @Test
        void 회원_접근() {
        }

        @Test
        void 회원가입_실패() {

        }

    }


    @Test
    void login() {
    }
}