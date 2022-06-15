package com.blog.server.blog.controller;

import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.service.ImageService;
import com.blog.server.blog.validaton.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @PostMapping("/api/image")
    public Object addImage(@RequestParam("images") MultipartFile multipartFile, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        String result = bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imageService.uploadImage(multipartFile);
        return Response.Image.builder().image_url(result).build();
    }
}