import { Component, OnInit, ɵresetCompiledComponents } from '@angular/core';
import { Song } from '../song';
import { CommonModule } from '@angular/common';
import { SongService } from '../song.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-song-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './song-list.component.html',
  styleUrl: './song-list.component.css'
})
export class SongListComponent implements OnInit {
  songList: Song[];
  
  constructor(private songService: SongService, private router: Router) {}

  ngOnInit(): void {
      this.getSongs();
  }

  updateSong(id: number) {
    this.router.navigate(['/update-song', id]);
  }

  private getSongs() {
    this.songService.getSongList().subscribe(data => {
      this.songList = data;
    })
  }
}
