import { Component, OnInit, ɵresetCompiledComponents } from '@angular/core';
import { Song } from '../song';
import { CommonModule } from '@angular/common';
import { SongService } from '../song.service';

@Component({
  selector: 'app-song-list',
  imports: [CommonModule],
  templateUrl: './song-list.component.html',
  styleUrl: './song-list.component.css'
})
export class SongListComponent implements OnInit {
  songList: Song[];
  
  constructor(private songService: SongService) {}

  ngOnInit(): void {
      this.getSongs();
  }

  private getSongs() {
    this.songService.getSongList().subscribe(data => {
      this.songList = data;
    })
  }
}
