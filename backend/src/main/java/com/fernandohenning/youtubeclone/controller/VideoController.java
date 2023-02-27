package com.fernandohenning.youtubeclone.controller;

import com.fernandohenning.youtubeclone.dto.UploadVideoResponse;
import com.fernandohenning.youtubeclone.dto.VideoDto;
import com.fernandohenning.youtubeclone.service.impl.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/videos/")
@RequiredArgsConstructor
public class VideoController {
    private final VideoServiceImpl videoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("video") MultipartFile file){
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("thumbnail") MultipartFile file, @RequestParam("videoId") String videoId){
        return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto){
        return videoService.editVideoMetaData(videoDto);
    }
}
