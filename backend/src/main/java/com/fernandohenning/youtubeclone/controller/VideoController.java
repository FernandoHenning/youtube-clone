package com.fernandohenning.youtubeclone.controller;

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
    public void uploadVideo(@RequestParam("file") MultipartFile file){
        videoService.uploadVideo(file);
    }
}
