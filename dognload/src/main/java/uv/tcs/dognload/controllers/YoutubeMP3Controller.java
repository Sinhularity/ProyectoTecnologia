package uv.tcs.dognload.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uv.tcs.dognload.model.YoutubeMP3Response;
import uv.tcs.dognload.services.YoutubeMP3Service;

@RestController
@RequestMapping("api/v1/songs/")
@CrossOrigin(origins = "http://localhost:4200/")
public class YoutubeMP3Controller {
    private final YoutubeMP3Service youtubeMP3Service;
    private final SongController songController;

    public YoutubeMP3Controller(YoutubeMP3Service youtubeMP3Service, SongController songController) {
        this.youtubeMP3Service = youtubeMP3Service;
        this.songController = songController;
    }

    @GetMapping("/download")
    public String downloadMP3(@RequestParam Long songID) {
        String songURL = songController.getSongByID(songID).getBody().getVideoURL();

        // formatear la URL del video para obtener solo el ID
        String urlID = songURL.substring(songURL.indexOf('=') + 1, songURL.indexOf('=') + 12).trim();
        // System.out.println(urlID);

        // realizar llamada a la api YoutubeMP3
        YoutubeMP3Response response = youtubeMP3Service.getMP3DownloadLink(urlID).block();

        if (response.getStatus().equals("ok")) {
            // System.out.println(response.getLink());
            return response.getLink();
        } else if (response.getStatus().equals("processing")) {
            return response.getStatus();
        }
        return null;
    }
}
