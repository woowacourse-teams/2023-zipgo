package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.KakaoOAuthResponse;
import zipgo.auth.OAuthResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String KAKAO_ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;
    private final RestTemplate restTemplate;

    @Override
    public String getAccessToken(String authCode) {
        HttpHeaders header = createRequestHeader();
        MultiValueMap<String, String> body = createRequestBodyWithAuthCode(authCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);

        return restTemplate.exchange(
                KAKAO_ACCESS_TOKEN_URI,
                POST,
                request,
                KakaoTokens.class
        ).getBody().getAccessToken();
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpHeaders(header);
    }

    private MultiValueMap<String, String> createRequestBodyWithAuthCode(String authCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", authCode);
        return new LinkedMultiValueMap<>(body);
    }

    @Override
    public OAuthResponse getMemberDetail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<HttpHeaders> requestHeader = new HttpEntity<>(headers);

        return restTemplate.exchange(
                KAKAO_USER_INFO_URI,
                GET,
                requestHeader,
                KakaoOAuthResponse.class
        ).getBody();
    }

}
