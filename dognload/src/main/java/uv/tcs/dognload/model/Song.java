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

    @Column(name = "title", length = 65, nullable = false)
    private String title;
    @Column(name = "videoURL", length = 255, nullable = false)
    private String videoURL;
    @Column(name = "downloadlink", columnDefinition = "TEXT")
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
