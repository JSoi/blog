package com.blog.server.blog.service;

import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("회원가입")
    class register_user {
        @AfterEach
        void clear(){
            userRepository.deleteAll();
        }
        @Test
        void 회원가입_성공() {
            //given
            UserService userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider);
            UserDto.Register userRegisterDto = UserDto.Register.builder()
                    .name("testname").email("testemail@testemail.com")
                    .introduce("hitest").password("testsuccess").nickname("testnickname")
                    .build();
            //when
            Response.Simple response = userService.register(userRegisterDto);

            //then
            assertThat(response.getCode()).isEqualTo(200);
            assertTrue(response.isResult());
        }

        @Nested
        @DisplayName("회원가입 실패")
        class RegisterUser_Fail {

            UserService userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider);
            @Test
            void 닉네임_중복() {
                //given
                UserDto.Register oldUserDto = UserDto.Register.builder()
                        .name("testname").email("testemail@testemail.com")
                        .introduce("hitest").password("testsuccess").nickname("testnickname")
                        .build();

                UserDto.Register userRegisterDto = UserDto.Register.builder()
                        .name("testname").email("testemail@testemail.com")
                        .introduce("hitest").password("testsuccess").nickname("testnickname")
                        .build();

            }

            @Test
            void 패스워드에_닉네임포함() {

            }
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