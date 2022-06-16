package com.blog.server.blog.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    WRONG_ID(BAD_REQUEST, "아이디가 존재하지 않습니다"),
    BAD_PW(BAD_REQUEST, "비밀번호에 별명을 포함하지 마세요"),
    BAD_LOGIN(BAD_REQUEST, "아이디 또는 패스워드를 확인해주세요"),
    LIKES_NOT_EXIST(BAD_REQUEST, "해당 LikeId는 존재하지 않습니다"),
    BAD_NICKNAME(BAD_REQUEST, "별명을 영어 대소문자와 숫자로 구성해주세요"),
    POST_NOT_EXIST(BAD_REQUEST, "해당 PostId는 존재하지 않습니다"),
    USER_NOT_EXIST(BAD_REQUEST, "해당 UserId는 존재하지 않습니다"),
    NEED_LOGIN_TO_LIKE(UNAUTHORIZED, "좋아요를 누르려면 로그인 해주세요"),
    NEED_LOGIN(UNAUTHORIZED, "로그인 해주세요"),
    COMMENT_NOT_EXIST(BAD_REQUEST, "해당 CommentId는 존재하지 않습니다"),
    IMAGE_ERROR(BAD_REQUEST, "이미지 업로드에 실패했습니다"),
    WRONG_IMAGE_FILENAME(BAD_REQUEST, "잘못된 이미지 이름입니다."),
    ALREADY_LOGGED_IN(LOCKED, "이미 로그인 되어있습니다"),
    EMPTY_INPUT(BAD_REQUEST, "빈 값을 입력하지 마세요"),
    EXIST_EMAIL(BAD_REQUEST, "중복된 아이디입니다");
    private final HttpStatus httpStatus;
    private final String detail;
}
