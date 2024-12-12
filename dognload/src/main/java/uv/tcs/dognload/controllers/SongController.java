package uv.tcs.dognload.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uv.tcs.dognload.repositories.SongRepo;
import uv.tcs.dognload.services.YoutubeMP3Service;
import uv.tcs.dognload.model.Song;
import uv.tcs.dognload.model.YoutubeMP3Response;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class SongController {
    @Autowired
    private SongRepo repo;

    @Autowired
    private YoutubeMP3Controller youtubeMP3Controller;

    @Autowired
    private YoutubeMP3Service youtubeMP3Service;

    // listar todas las canciones descargadas
    @GetMapping("/songs")
    public List<Song> listAll() {
        return repo.findAll();
    }

    // guardar cancion
    @PostMapping("/songs")
    public Song saveSong(@RequestBody Song song) {
        System.out.println("cancion guardada");

        try {
            song.setDownloadlink(getDownloadLink("W9GaIbECisQ"));
        } catch (Exception e) {
            System.out.println("Intenta de nuevo...");
            return new Song();
        }
        return repo.save(song);
    }

    private String getDownloadLink(String videoID) {
        YoutubeMP3Response response = youtubeMP3Service.getMP3DownloadLink(videoID).block();
        System.out.println(response.getStatus());
        
        if (response.getStatus().equals("ok")) {
            System.out.println(response.getLink());
            return response.getLink();
        } else {
            throw new RuntimeException("Error");
        }
    }

    @GetMapping("songs/{id}")
    public ResponseEntity<Song> getSongByID(@PathVariable Long id) {
        Song song = repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        return ResponseEntity.ok(song);
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song songDetail) {
        Song song = repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        song.setTitle(songDetail.getTitle());
        song.setVideoURL(songDetail.getVideoURL());

        Song updatedSong = repo.save(song);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSong(@PathVariable Long id) {
        Song song = repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));

        repo.delete(song);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
