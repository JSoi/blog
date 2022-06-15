package com.blog.server.blog.excpetion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.blog.server.blog.excpetion.ErrorResponse.argumentErrors;

@RestControllerAdvice
public class ArgumentException {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException ex) {
        String responseStr = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return argumentErrors(responseStr); // 2
    }
}
