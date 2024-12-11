package uv.tcs.dognload.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cancion")
public class Song {
    @Id
    private long id;
    private String title;
    private String downloadlink;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDownloadlink() {
        return downloadlink;
    }
    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }

    
}
