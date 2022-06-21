package com.blog.server.blog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    String bucketName = "bucketName";
    String bucket = "bucket";
    @Mock
    AmazonS3 amazonS3;
    @InjectMocks
    ImageService imageService;

    @Test
    void URL_반환_확인() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("sample.png", new ClassPathResource("/static/img/sample.png").getInputStream());

    }

}