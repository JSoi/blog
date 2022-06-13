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
    BAD_NICKNAME(BAD_REQUEST, "별명을 영어 대소문자와 숫자로 구성해주세요"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
    POST_NOT_EXIST(BAD_REQUEST, "해당 PostId는 존재하지 않습니다"),
    USER_NOT_EXIST(BAD_REQUEST, "해당 UserId는 존재하지 않습니다"),
    COMMENT_NOT_EXIST(BAD_REQUEST, "해당 CommentId는 존재하지 않습니다");


    private final HttpStatus httpStatus;
    private final String detail;
}
