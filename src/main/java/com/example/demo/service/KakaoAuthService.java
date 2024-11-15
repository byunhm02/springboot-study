package com.example.demo.service;
import com.example.demo.dto.KakaoTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoAuthService {
    private String KAKAO_CLIENT_ID = "69611c537135468129dbf23caa99c358"; // 실제 client_id로 변경
    private String KAKAO_REDIRECT_URI = "http://localhost:8080/callback";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_SECRET = "sfmP6hgRzBwemYomPjioY1EnedSH0dXu";

    //받아온 인가코드를 이용해 엑세스 토큰 발급
    public String getAccessTokenFromKakao(String code) {
        System.out.println("getAccessTokenFromKakao진입성공");

        WebClient webClient = WebClient.create();
        System.out.println("getAccessTokenFromKakao진입성공");

        String uri = String.format(
                "https://kauth.kakao.com/oauth/token?grant_type=%s&client_id=%s&redirect_uri=%s&code=%s&client_secret=%s",
                GRANT_TYPE, KAKAO_CLIENT_ID, KAKAO_REDIRECT_URI, code,CLIENT_SECRET
        );
        System.out.println(uri);



        KakaoTokenResponseDTO response = webClient.post()
                .uri(uri)
                /*
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("kauth.kakao.com")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("redirect_uri", KAKAO_REDIRECT_URI)
                        .queryParam("code", code)
                        .build())

                 */
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(KakaoTokenResponseDTO.class)
                .block();

        if (response != null) {
            log.info("Access Token!!: {}", response.getAccessToken());
            return response.getAccessToken();
        }
        throw new RuntimeException("Failed to get access token from Kakao");
    }


}
