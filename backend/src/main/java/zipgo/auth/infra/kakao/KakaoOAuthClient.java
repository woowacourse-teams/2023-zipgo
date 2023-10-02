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
import zipgo.auth.application.OAuthClient;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.exception.OAuthResourceNotBringException;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.infra.kakao.config.KakaoCredentials;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;
import zipgo.auth.infra.kakao.dto.KakaoTokenResponse;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoCredentials kakaoCredentials;
    private final RestTemplate restTemplate;

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
        try {
            return restTemplate.exchange(
                    USER_INFO_URI,
                    GET,
                    request,
                    KakaoMemberResponse.class
            );
        } catch (HttpClientErrorException e) {
            throw new OAuthResourceNotBringException();
        }
    }

}
