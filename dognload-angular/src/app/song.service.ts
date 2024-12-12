import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Song } from './song';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  
  private baseURL = "http://localhost:8080/api/v1/songs";

  constructor(private httpClient: HttpClient) { }

  getSongList():Observable<Song[]> {
    return this.httpClient.get<Song[]>(`${this.baseURL}`)
  }

  saveSong(song: Song): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, song);
  }

  updateSong(id: number, song: Song): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, song);
  }

  getSongByID(id: number): Observable<Song> {

    return this.httpClient.get<Song>(`${this.baseURL}/${id}`);
  }

  deleteSong(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }

  downloadSong(id:number): Observable<string> {
    return this.httpClient.get(`${this.baseURL}/download?songID=${id}`, {responseType: 'text'});
  }
}
