import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SongListComponent } from './song-list/song-list.component';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'DogNLoad';
}
