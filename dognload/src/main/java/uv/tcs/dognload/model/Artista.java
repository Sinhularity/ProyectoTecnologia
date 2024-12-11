package uv.tcs.dognload.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artista")
public class Artista {
    @Id
    private long id;
    private String name;
}
