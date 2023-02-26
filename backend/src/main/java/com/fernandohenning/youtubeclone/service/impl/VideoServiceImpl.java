package com.fernandohenning.youtubeclone.service.impl;

import com.fernandohenning.youtubeclone.entities.video.Video;
import com.fernandohenning.youtubeclone.repository.VideoRepository;
import com.fernandohenning.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;


    @Override
    public void uploadVideo(MultipartFile multipartFile){
        Video video = new Video();
        String videoUrl =  s3Service.uploadFile(multipartFile);
        video.setUrl(videoUrl);
        videoRepository.save(video);
    }
}
