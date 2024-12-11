package uv.tcs.dognload.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import uv.tcs.dognload.model.Song;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
    
}
