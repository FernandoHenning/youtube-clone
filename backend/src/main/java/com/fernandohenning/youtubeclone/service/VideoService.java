package com.fernandohenning.youtubeclone.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    void uploadVideo(MultipartFile multipartFile);
}
