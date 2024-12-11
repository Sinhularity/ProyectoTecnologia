package uv.tcs.dognload.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uv.tcs.dognload.model.Artista;

@Repository
public interface ArtistaRepo extends JpaRepository<Artista, Long> {
    
}
