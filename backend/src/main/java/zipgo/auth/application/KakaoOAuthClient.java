package zipgo.auth.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.application.dto.KakaoMemberResponse;
import zipgo.auth.application.dto.KakaoTokenResponse;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.exception.AuthException;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    public static final String KAKAO_ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    public static final String GRANT_TYPE = "authorization_code";

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

        ResponseEntity<KakaoTokenResponse> kakaoTokenResponse = getKakaoToken(request);

        return Objects.requireNonNull(kakaoTokenResponse.getBody()).accessToken();
    }

    HttpHeaders createRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_FORM_URLENCODED);
        return new HttpHeaders(header);
    }

    MultiValueMap<String, String> createRequestBodyWithAuthCode(String authCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", authCode);
        return new LinkedMultiValueMap<>(body);
    }

    private ResponseEntity<KakaoTokenResponse> getKakaoToken(HttpEntity<MultiValueMap<String, String>> request) {
        ResponseEntity<KakaoTokenResponse> exchange;
        try {
            exchange = restTemplate.exchange(
                    KAKAO_ACCESS_TOKEN_URI,
                    POST,
                    request,
                    KakaoTokenResponse.class
            );
        } catch (HttpClientErrorException | NullPointerException e) {
            throw new AuthException.KakaoNotFound("카카오 토큰을 가져오는 중 에러가 발생했습니다.", e);
        }
        return exchange;
    }

    @Override
    public OAuthMemberResponse getMember(String accessToken) {
        HttpEntity<HttpHeaders> request = createRequest(accessToken);

        ResponseEntity<KakaoMemberResponse> response = getKakaoMember(request);

        return response.getBody();
    }

    private HttpEntity<HttpHeaders> createRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<KakaoMemberResponse> getKakaoMember(HttpEntity<HttpHeaders> request) {
        ResponseEntity<KakaoMemberResponse> response;
        try {
            response = restTemplate.exchange(
                    KAKAO_USER_INFO_URI,
                    GET,
                    request,
                    KakaoMemberResponse.class
            );
        } catch (HttpClientErrorException e) {
            throw new AuthException.KakaoNotFound("카카오 사용자 정보를 가져오는 중 에러가 발생했습니다", e);
        }
        return response;
    }

}
