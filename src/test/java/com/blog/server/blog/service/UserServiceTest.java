package com.blog.server.blog.service;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.dto.UserDto;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.security.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@Transactional
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    UserService userService;

    @Nested
    @DisplayName("회원가입")
    class register_user {
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
            @Test
            void 이메일_중복() {
                //given
                UserDto.Register newComer = UserDto.Register.builder()
                        .name("testname").email("testemail@testemail.com")
                        .introduce("hitest").password("testpassword").nickname("testnickname")
                        .build();
                ;
                //when
                when(userRepository.existsUserByEmail("testemail@testemail.com")).thenReturn(true);
                //then
                BlogException blogException = assertThrows(BlogException.class, () -> {
                    userService.register(newComer);
                });
                assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.EXIST_EMAIL);
            }

            @Test
            void 패스워드에_닉네임포함() {
                //given
                UserDto.Register newComer = UserDto.Register.builder()
                        .name("testname").email("testemail@testemail.com")
                        .introduce("hitest").password("testpasswordnickname").nickname("nickname")
                        .build();
                //when & then
                BlogException blogException = assertThrows(BlogException.class, () -> {
                    userService.register(newComer);
                });
                assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.BAD_NICKNAME);
            }
        }

    }


    @Nested
    @DisplayName("로그인")
    class Login {
        @Test
        void 로그인_성공() {
            //given
            String testEmail = "testemail@email.com";
            String testPassword = "testpassword";
            UserDto.Login loginDto = UserDto.Login.builder().email(testEmail).password(testPassword).build();
            //when
            User user = User.builder().email(testEmail).password(testPassword).roles(Collections.singletonList("ROLE_USER")).build();
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(testPassword, user.getPassword())).thenReturn(true);
            when(jwtTokenProvider.createToken(user.getEmail(), user.getRoles())).thenReturn("mytoken");
            //then
            Response.Login response = userService.login(loginDto);
            assertTrue(response.isResult());
            assertThat(response.getNickname()).isEqualTo(user.getNickname());
            assertThat(response.getUserToken()).isEqualTo("mytoken");
        }

        @Test
        void 없는_아이디() {
            //given
            UserDto.Login loginDto = UserDto.Login.builder().email("test@test.com").password("testpassword").build();
            //when
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
            //then
            BlogException blogException = assertThrows(BlogException.class, () -> {
                userService.login(loginDto);
            });
            assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.BAD_LOGIN);

            //then
        }

        @Test
        void 틀린_비밀번호() {
            User user = User.builder().email("test@test.com").password("testpassword").roles(Collections.singletonList("ROLE_USER")).build();
            UserDto.Login loginDto = UserDto.Login.builder().email("test@test.com").password("testpassword").build();
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
            //when
            BlogException blogException = assertThrows(BlogException.class, () -> {
                userService.login(loginDto);
            });
            assertThat(blogException.getErrorCode()).isEqualTo(ErrorCode.BAD_LOGIN);
        }

    }
}