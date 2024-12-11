package uv.tcs.dognload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import uv.tcs.dognload.repositories.SongRepo;
import uv.tcs.dognload.model.Song;

@RestController
@RequestMapping("api/v1/")
public class SongController {
    @Autowired
    private SongRepo repo;

    @GetMapping("/songs")
    public List<Song> listAll() {
        return repo.findAll();
    }
}
