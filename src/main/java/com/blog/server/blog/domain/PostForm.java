package com.blog.server.blog.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Validated
@Getter @Setter
public class PostForm {
    @NotBlank(message = "제목에 값을 입력하세요")
    private String title;
    @NotBlank(message = "내용에 값을 입력하세요")
    private String content;
    private MultipartFile image = null;
    private Long template = 0L;
}
