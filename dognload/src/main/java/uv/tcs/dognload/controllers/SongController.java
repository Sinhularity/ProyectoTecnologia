package uv.tcs.dognload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import uv.tcs.dognload.repositories.SongRepo;
import uv.tcs.dognload.model.Song;

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
        return repo.save(song);
    }
}
