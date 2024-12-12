import { Component } from '@angular/core';
import { Song } from '../song';
import { SongService } from '../song.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2'
import { catchError,of,tap} from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-song',
  imports: [FormsModule],
  templateUrl: './update-song.component.html',
  styleUrl: './update-song.component.css'
})

export class UpdateSongComponent {
  id:number;
  song: Song = new Song();
  
  constructor(private songService: SongService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];

      this.songService.getSongByID(this.id).pipe(
        tap(data => {
          this.song = data;
        }),
          catchError(error => {
            console.log("errorrrr")
            console.error(error);
            return of(null);
          })
      ).subscribe();
  }

  goToSongList() {
    this.router.navigate(['/songs']);
    Swal.fire('Cancion actualizada');
  }

  onSubmit() {
    if (this.song) {
      this.songService.updateSong(this.id, this.song).pipe(
        tap(data => {
          this.goToSongList();
        }),
          catchError(error => {
            console.error('Error updating song', error);
            return of(null)
          })
      ).subscribe();
    }
  }
}
