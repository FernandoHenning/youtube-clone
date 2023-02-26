package com.fernandohenning.youtubeclone.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fernandohenning.youtubeclone.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3Service implements FileService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file)  {
        log.info("[S3 Service] Starting upload...");
        String fileExtension = getFileExtension(file);
        String key = getFileKey(fileExtension);
        ObjectMetadata metadata = getFileMetaData(file);
        log.info("[S3 Service] Uploading file with key = {}", key);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);

        } catch (IOException e){
            log.error("[S3 Service] Error while uploading video: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An exception occurred while uploading the file");
        }
        amazonS3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        URL s3Url = amazonS3.getUrl(bucketName, key);
        log.info("File uploaded successfully.");
        return s3Url.toExternalForm();
    }


    private ObjectMetadata getFileMetaData(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }


    private String getFileExtension(MultipartFile file) {
        return StringUtils.getFilenameExtension(file.getOriginalFilename());
    }


    private String getFileKey(String fileExtension){
        return  UUID.randomUUID().toString().concat(".").concat(fileExtension);
    }
}
