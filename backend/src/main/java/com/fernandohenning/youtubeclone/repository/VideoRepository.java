package com.fernandohenning.youtubeclone.repository;

import com.fernandohenning.youtubeclone.entities.video.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
