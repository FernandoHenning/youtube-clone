package com.fernandohenning.youtubeclone.service;

import com.fernandohenning.youtubeclone.dto.UploadVideoResponse;
import com.fernandohenning.youtubeclone.dto.VideoDto;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    UploadVideoResponse uploadVideo(MultipartFile multipartFile);

    VideoDto editVideoMetaData(VideoDto videoDto);

    String uploadThumbnail(MultipartFile file, String videoId);
}
