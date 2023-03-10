import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDto} from "../video-dto";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent implements OnInit {
  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('')
  videoStatus: FormControl = new FormControl('')
  selectedThumbnail!: File;
  thumbnailFileName!: string;
  addOnBlur = true;
  videoId = '';
  URL: FileReader | null | undefined ;
  videoUrl!: string;
  thumbnalUrl!: string;

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService, private snackBar: MatSnackBar) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(data=>{
      this.videoUrl = data.videoUrl;
      this.thumbnalUrl = data.thumbnailUrl;
    })
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus,
    })
  }

  ngOnInit(): void {
  }

  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tags: string): void {
    const index = this.tags.indexOf(tags);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onThumbnailSelected(event: Event) {
    // @ts-ignore
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();

      // @ts-ignore
      this.selectedThumbnail = event.target?.files[0];
      this.thumbnailFileName = this.selectedThumbnail?.name;
      // @ts-ignore
      reader.readAsDataURL(event.target.files[0]); // Read file as data url
      reader.onloadend = (e) => { // function call once readAsDataUrl is completed
        // @ts-ignore
        this.URL = e.target['result']; // Set image in element

      };
    }
  }


  uploadThumbnail() {
    this.videoService.uploadThumbnail(this.selectedThumbnail, this.videoId).subscribe(data =>{
      this.openSnackBar("Thumbnail uploaded sucessfully", "OK")
      this.thumbnalUrl = data;
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  saveVideo() {
    const videoMetaData : VideoDto = {
      "id":this.videoId,
      "title": this.saveVideoDetailsForm.get('title')?.value,
      "description": this.saveVideoDetailsForm.get('description')?.value,
      "tags": this.tags,
      "videoStatus": this.saveVideoDetailsForm.get('videoStatus')?.value,
      "videoUrl": this.videoUrl,
      "thumbnailUrl": this.thumbnalUrl
    }
    console.log(videoMetaData)
    this.videoService.saveVideo(videoMetaData).subscribe(data=>{
      this.openSnackBar("Video details saved!", "OK")
    });
  }
}
