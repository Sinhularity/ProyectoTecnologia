import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Song } from '../song';
import { SongService } from '../song.service';
import { Router } from 'express';

@Component({
  selector: 'app-download-song',
  imports: [FormsModule],
  templateUrl: './download-song.component.html',
  styleUrl: './download-song.component.css'
})
export class DownloadSongComponent implements OnInit {

  song: Song = new Song();
  constructor(private songService: SongService, private router: Router) {}

  ngOnInit(): void {
      console.log(this.song);
  }

  saveSong() {
    this.songService.saveSong(this.song).subscribe(data => {
      console.log(data)
    })
  }

  onSubmit() {
    console.log(this.song);
  }
}
