import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UploadVideoResponse} from "./upload-video/UploadVideoResponse";
import { environment } from '../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }
  uploadVideoApiUrl = environment.UPLOAD_VIDEO_API_URL
  uploadThumbnailApiUrl = environment.UPLOAD_THUMBNAIL_API_URL

  uploadVideo(fileEntry: File): Observable<UploadVideoResponse>{
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);

    return this.httpClient.post<UploadVideoResponse>(this.uploadVideoApiUrl, formData )
  }

  uploadThumbnail(fileEntry: File, videoId: string): Observable<string>{
    const formData = new FormData()
    formData.append('thumbnail', fileEntry, fileEntry.name);
    formData.append('videoId', videoId);

    return this.httpClient.post(this.uploadThumbnailApiUrl, formData, {
      responseType: 'text'
    })
  }
}
