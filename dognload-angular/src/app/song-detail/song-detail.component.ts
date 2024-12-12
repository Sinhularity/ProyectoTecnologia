import { Component, OnInit } from '@angular/core';
import { Song } from '../song';
import { ActivatedRoute } from '@angular/router';
import { SongService } from '../song.service';

@Component({
  selector: 'app-song-detail',
  imports: [],
  templateUrl: './song-detail.component.html',
  styleUrl: './song-detail.component.css'
})
export class SongDetailComponent implements OnInit {
  
  id:number;
  song: Song;

  constructor(private route:ActivatedRoute, private songService: SongService) { }

  ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.song = new Song();
      this.songService.getSongByID(this.id).subscribe(data => {
        this.song = data
      })
  }
}
