package com.blog.server.blog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final List<String> imgLst = Arrays.asList(".jpg", ".png", ".jpeg", ".bmp");

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID().toString().concat(getFileExtension(multipartFile.getOriginalFilename()));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new BlogException(ErrorCode.IMAGE_ERROR);
        }
        return bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
    }


    public void deleteImage(String fileName) {
        String specFileName = fileName.replaceFirst(bucketName + ".s3.ap-northeast-2.amazonaws.com/", "");
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, specFileName));
    }

    private String getFileExtension(String fileName) {
        fileName = fileName.toLowerCase();
        String target;
        try {
            target = fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new BlogException(ErrorCode.WRONG_IMAGE_FILENAME);
        }
        if (imgLst.contains(target)) {
            return target;
        } else {
            throw new BlogException(ErrorCode.WRONG_FILE_TYPE);
        }

    }
}
