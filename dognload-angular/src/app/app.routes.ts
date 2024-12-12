import { Routes } from '@angular/router';
import { SongListComponent } from './song-list/song-list.component';
import { DownloadSongComponent } from './download-song/download-song.component';
import { UpdateSongComponent } from './update-song/update-song.component';
import { SongDetailComponent } from './song-detail/song-detail.component';

export const routes: Routes = [
    {path: 'songs', component:SongListComponent}
    , {path: '', redirectTo:'songs', pathMatch:'full'}
    , {path: 'add-song', component: DownloadSongComponent}
    , {path: 'update-song/:id', component: UpdateSongComponent}
    , {path: 'song-detail/:id', component: SongDetailComponent}
];
