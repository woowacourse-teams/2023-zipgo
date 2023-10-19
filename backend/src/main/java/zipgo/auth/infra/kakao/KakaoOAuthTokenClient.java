package zipgo.auth.infra.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.domain.OAuthTokenClient;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.infra.kakao.config.KakaoCredentials;
import zipgo.auth.infra.kakao.dto.KakaoTokenResponse;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;


@Component
@RequiredArgsConstructor
public class KakaoOAuthTokenClient implements OAuthTokenClient {

    private static final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";

    private final RestTemplate restTemplate;
    private final KakaoCredentials kakaoCredentials;

    @Override
    public String getAccessToken(String authCode, String redirectUri) {
        HttpHeaders header = createRequestHeader();
        MultiValueMap<String, String> body = createRequestBodyWithAuthCode(authCode, redirectUri);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);
        ResponseEntity<KakaoTokenResponse> kakaoTokenResponse = getKakaoToken(request);

        return requireNonNull(requireNonNull(kakaoTokenResponse.getBody())).accessToken();
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_FORM_URLENCODED);
        return header;
    }

    private MultiValueMap<String, String> createRequestBodyWithAuthCode(String authCode, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoCredentials.getClientId());
        body.add("redirect_uri", redirectUri);
        body.add("client_secret", kakaoCredentials.getClientSecret());
        body.add("code", authCode);
        return body;
    }

    private ResponseEntity<KakaoTokenResponse> getKakaoToken(HttpEntity<MultiValueMap<String, String>> request) {
        try {
            return restTemplate.exchange(
                    ACCESS_TOKEN_URI,
                    POST,
                    request,
                    KakaoTokenResponse.class
            );
        } catch (HttpClientErrorException e) {
            throw new OAuthTokenNotBringException();
        }
    }
}
