package com.blog.server.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Validated
@Getter
@Setter
public class PostFormDto {
    @NotBlank(message = "제목에 값을 입력하세요")
    @Size(max = 25, message = "제목은 25 이하로 작성해주세요")
    private String title;

    @NotBlank(message = "내용에 값을 입력하세요")
    @Size(max = 3000, message = "내용은 3000자 이하로 작성해주세요")
    private String content;

    private MultipartFile image;

    private Long template = 1L;
}
