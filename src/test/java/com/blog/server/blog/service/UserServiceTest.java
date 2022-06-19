package com.blog.server.blog.service;

import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
//@Transactional
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    UserService userService;

    @Nested
    @DisplayName("회원가입")
    class register_user {
        @BeforeEach
        void init() {
            userRepository.deleteAll();
            userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider);
        }

        @Test
        void 회원가입_성공() {
            //given
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
            @AfterEach
            void clear() {
                userRepository.deleteAll();
            }

            @Test
            void 이메일_중복() {
                //given
                UserDto.Register oldDto = UserDto.Register.builder().name("testname").email("testemail@testemail.com")
                        .introduce("hitest").password("testsuccess").nickname("testnickname")
                        .build();
                userService.register(oldDto);

                //when
                UserDto.Register userRegisterDto = UserDto.Register.builder()
                        .name("testname2").email("testemail@testemail.com")
                        .introduce("hitest2").password("testsuccess2").nickname("testnickname2")
                        .build();

                assertThrows(BlogException.class, () -> {
                    userService.register(userRegisterDto);
                });
                assertEquals(2L, userRepository.count());
                //then

            }

            @Test
            void 패스워드에_닉네임포함() {
                UserDto.Register userRegisterDto = UserDto.Register.builder()
                        .name("testname2").email("testemail@testemail.com")
                        .introduce("hitest2").password("testnickname2").nickname("testnickname2")
                        .build();
                assertThrows(BlogException.class, () -> {
                    userService.register(userRegisterDto);
                });
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