import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Song } from '../song';
import { SongService } from '../song.service';
import { Router } from '@angular/router';

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
      console.log(data);
      this.goToSongList()
    }, error => console.log(error))
  }

  goToSongList() {
    this.router.navigate(['/songs'])
  }

  onSubmit() {
    this.saveSong();
  }
}
