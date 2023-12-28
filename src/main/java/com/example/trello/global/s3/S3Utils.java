package com.example.trello.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.trello.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Utils {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile file) {
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            String filename = UUID.randomUUID() + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            amazonS3Client.putObject(bucket,filename,file.getInputStream(),metadata);

            return filename;
        } catch(IOException e){
            throw new CustomException(HttpStatus.CONFLICT,"파일 업로드에 실패했습니다,");
        }

    }

    public boolean deleteFile(String filename){
        amazonS3Client.deleteObject(bucket,filename);
        return !amazonS3Client.doesObjectExist(bucket, filename);
    }

    public String getFileURL(String filename){
        return "https://chickenbeer-s3-bucket.s3.ap-northeast-2.amazonaws.com"  + "/" + filename;
    }

    private String getExtension(String originalFilename){
        int idx = originalFilename.lastIndexOf('.');
        return originalFilename.substring(idx+1);
    }
}
