package com.blog.server.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Validated
public class CommentDto {
    @NotBlank(message = "댓글에 내용을 입력하세요")
    @Size(max = 1000, message = "내용을 1000자 이하로 입력하세요")
    String content;
}