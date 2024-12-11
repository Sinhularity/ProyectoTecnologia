package uv.tcs.dognload.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import uv.tcs.dognload.model.YoutubeMP3Response;

import org.springframework.beans.factory.annotation.Value;

@Service
public class YoutubeMP3Service {
    private final WebClient webClient;

    @Value("${youtubemp3.url}")
    private String apiUrl;

    @Value("${youtubemp3.key}")
    private String apiKey;

    @Value("${youtubemp3.host}")
    private String apiHost;

    public YoutubeMP3Service(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<YoutubeMP3Response> getMP3DownloadLink(String videoID) {
        return webClient.get()
            .uri(apiUrl + videoID)
            .header("x-rapidapi-key", apiKey)
            .header("x-rapidapi-host", apiHost)
            .retrieve()
            .bodyToMono(YoutubeMP3Response.class);
    }
}
