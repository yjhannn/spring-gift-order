package gift.service;

import gift.kakaologin.KakaoResponse;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoLoginService {
    @Value("${kakao.client-id}")
    private String restKey;

    @Value("${kakao.redirect-url}")
    private  String redirectUrl;

    private final RestClient restClient;
    public KakaoLoginService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String getKakaoUrl() {
        return "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + restKey +
                "&redirect_uri=" + redirectUrl +
                "&response_type=code";
    }

    public String getAccessToken(String code) {
        var url = "https://kauth.kakao.com/oauth/token";
        final var body = createBody(code);
        var response = restClient.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .toEntity(KakaoResponse.class);
        return response.getBody().getAccessToken();
    }

    private @NotNull LinkedMultiValueMap<String, String> createBody(String code) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", restKey);
        body.add("redirect_url", redirectUrl);
        body.add("code", code);
        return body;
    }
}
