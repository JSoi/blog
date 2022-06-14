package com.blog.server.blog.validaton;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    //- 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    //- 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
    public static void validateRegisterUser(String nickName, String password) {
        if (nickName.length() < 3 || !Pattern.matches("^[a-zA-Z0-9]*$", nickName)) {
            throw new BlogException(ErrorCode.BAD_NICKNAME);
        }
        if (password.length() < 4 || password.contains(nickName)) {
            throw new BlogException(ErrorCode.BAD_PW);
        }
    }

    public static void validateLoginUser(User user) {
        if (user==null){
            throw new BlogException(ErrorCode.NEED_LOGIN);
        }
    }
}
