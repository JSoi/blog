package com.blog.server.blog.excpetion;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.Set;

import static com.blog.server.blog.excpetion.ErrorResponse.argumentErrors;

@RestControllerAdvice
@RestController
public class ArgumentException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException ex) {
        String responseStr = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return argumentErrors(responseStr); // 2
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> validExceptionNotControlled(ConstraintViolationException cve) {
        return argumentErrors(cve.getConstraintViolations().iterator().next().getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return argumentErrors(ErrorCode.IMAGE_SIZE_EXCEEDED.getDetail());
    }
}
