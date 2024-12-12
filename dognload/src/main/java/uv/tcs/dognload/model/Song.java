package uv.tcs.dognload.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cancion")
public class Song {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titulo_cancion", length = 65, nullable = false)
    private String title;
    @Column(name = "videoURL_cancion", length = 255, nullable = false)
    private String videoURL;
}
