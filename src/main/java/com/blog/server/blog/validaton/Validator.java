package com.blog.server.blog.validaton;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class Validator {
    //- 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    //- 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
    public static void validateRegisterUser(String nickName, String password) {
        if (password.contains(nickName)) {
            throw new BlogException(ErrorCode.BAD_PW);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static void validateLoginUser(User user, ErrorCode code) {
        if (user == null) {
            throw new BlogException(code);
        }
    }

    @ResponseStatus(HttpStatus.LOCKED)
    public static void alreadyLoggedIn(User user, ErrorCode code) {
        if (user != null) {
            throw new BlogException(code);
        }
    }
}
