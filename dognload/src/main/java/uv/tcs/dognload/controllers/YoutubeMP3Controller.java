package uv.tcs.dognload.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import uv.tcs.dognload.model.YoutubeMP3Response;
import uv.tcs.dognload.services.YoutubeMP3Service;

@RestController
public class YoutubeMP3Controller {
    private final YoutubeMP3Service youtubeMP3Service;

    public YoutubeMP3Controller(YoutubeMP3Service youtubeMP3Service) {
        this.youtubeMP3Service = youtubeMP3Service;
    }

    @GetMapping("http://localhost:4200/songs")
    public Mono<YoutubeMP3Response> downloadMP3(@RequestParam String videoID) {
        return youtubeMP3Service.getMP3DownloadLink(videoID);
    }
}
