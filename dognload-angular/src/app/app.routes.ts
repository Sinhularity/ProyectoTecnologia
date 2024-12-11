import { Routes } from '@angular/router';
import { SongListComponent } from './song-list/song-list.component';
import { DownloadSongComponent } from './download-song/download-song.component';

export const routes: Routes = [
    {path: 'songs', component:SongListComponent}
    , {path: '', redirectTo:'songs', pathMatch:'full'}
    , {path: 'download-song', component: DownloadSongComponent}
];
