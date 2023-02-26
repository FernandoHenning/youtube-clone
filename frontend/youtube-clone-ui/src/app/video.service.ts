import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }
  apiUrl = "http://localhost:8080/api/v1/videos/"

  uploadVideo(fileEntry: File): Observable<any>{
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);

    return this.httpClient.post(this.apiUrl, formData )
  }
}
