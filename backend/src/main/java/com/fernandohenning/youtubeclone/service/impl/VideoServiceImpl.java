package com.fernandohenning.youtubeclone.service.impl;

import com.fernandohenning.youtubeclone.dto.UploadVideoResponse;
import com.fernandohenning.youtubeclone.dto.VideoDto;
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
    public UploadVideoResponse uploadVideo(MultipartFile multipartFile){
        Video video = new Video();
        String videoUrl =  s3Service.uploadFile(multipartFile);
        video.setUrl(videoUrl);
        Video uploadedVideo =  videoRepository.save(video);
        return new UploadVideoResponse(uploadedVideo.getId(), uploadedVideo.getUrl());
    }

    @Override
    public VideoDto editVideoMetaData(VideoDto videoDto) {
        Video editedVideo = getVideoById(videoDto.getId());

        mapVideoDtoToEntity(videoDto, editedVideo);

        videoRepository.save(editedVideo);
        return videoDto;
    }

    @Override
    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video video = getVideoById(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(video);
        return thumbnailUrl;

    }

    private static void mapVideoDtoToEntity(VideoDto videoDto, Video editedVideo) {
        editedVideo.setTitle(videoDto.getTitle());
        editedVideo.setDescription(videoDto.getDescription());
        editedVideo.setTags(videoDto.getTags());
        editedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        editedVideo.setVideoStatus(videoDto.getVideoStatus());
    }

    private Video getVideoById(String videoId) {
        return videoRepository.findById(videoId).
                orElseThrow(() -> new IllegalArgumentException("Cannot find video with id: ".concat(videoId)));
    }
}
