package uv.tcs.dognload.controllers;

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
import uv.tcs.dognload.model.Song;
import uv.tcs.dognload.exceptions.ResourceNotFoundException;;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class SongController {
    @Autowired
    private SongRepo repo;

    // listar todas las canciones descargadas
    @GetMapping("/songs")
    public List<Song> listAll() {
        return repo.findAll();
    }

    // guardar cancion
    @PostMapping("/songs")
    public Song saveSong(@RequestBody Song song) {
        System.out.println("cancion guardada");
        return repo.save(song);
    }

    // obtener cancion por id
    @GetMapping("songs/{id}")
    public ResponseEntity<Song> getSongByID(@PathVariable Long id) {
        Song song = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        return ResponseEntity.ok(song);
    }

    // actualizar datos de una cancion
    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song songDetail) {
        Song song = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        song.setTitle(songDetail.getTitle());
        song.setVideoURL(songDetail.getVideoURL());

        Song updatedSong = repo.save(song);
        return ResponseEntity.ok(updatedSong);
    }

    // eliminar cancion
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSong(@PathVariable Long id) {
        Song song = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        repo.delete(song);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
