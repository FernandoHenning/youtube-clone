import {Component, Input, OnInit} from '@angular/core';
import {VgApiService} from "@videogular/ngx-videogular/core";

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnInit{
  preload: string = 'auto';
  api!: VgApiService;
  @Input()
  videoUrl!: string;
  ngOnInit(): void {
  }

  onPlayerReady(api: VgApiService) {
    this.api = api;

    this.api.getDefaultMedia().subscriptions.ended.subscribe(
      () => {
        // Set the video to the beginning
        this.api.getDefaultMedia().currentTime = 0;
      }
    );
  }


}
