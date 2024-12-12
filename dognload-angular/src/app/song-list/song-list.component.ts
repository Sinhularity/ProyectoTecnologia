import { Component, OnInit, ɵresetCompiledComponents } from '@angular/core';
import { Song } from '../song';
import { CommonModule } from '@angular/common';
import { SongService } from '../song.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

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

  downloadSong(id: number) {
    this.songService.downloadSong(id).subscribe(data => {
      console.log(data);

      if (data) {
        if (data === "processing") {
          Swal.fire("Peticion en proceso...", "Espera un momento para descargar");
        } else {
          const link = document.createElement('a');
          link.href = data;
          link.click();
        }
      } else {
        Swal.fire("Error durante la descarga", "Revisa el URL de la cancion");
      }
    })
  }

  deleteSong(id: number) {
    this.songService.deleteSong(id).subscribe(data => {
      console.log(data);
      this.getSongs();
    })
  }

  getSongDetail(id: number) {
    this.router.navigate(['song-detail', id])
  }

  private getSongs() {
    this.songService.getSongList().subscribe(data => {
      this.songList = data;
    })
  }
}
