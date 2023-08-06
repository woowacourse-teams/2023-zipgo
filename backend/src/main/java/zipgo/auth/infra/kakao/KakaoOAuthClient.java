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
import zipgo.auth.exception.AuthException;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;
import zipgo.auth.infra.kakao.dto.KakaoTokenResponse;
import zipgo.auth.infra.kakao.config.KakaoCredentials;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    public static final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    public static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    public static final String GRANT_TYPE = "authorization_code";

    private final KakaoCredentials kakaoCredentials;
    private final RestTemplate restTemplate;

    @Override
    public String getAccessToken(String authCode) {
        HttpHeaders header = createRequestHeader();
        MultiValueMap<String, String> body = createRequestBodyWithAuthCode(authCode);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);

        ResponseEntity<KakaoTokenResponse> kakaoTokenResponse = getKakaoToken(request);

        return requireNonNull(requireNonNull(kakaoTokenResponse.getBody())).accessToken();
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_FORM_URLENCODED);
        return new HttpHeaders(header);
    }

    private MultiValueMap<String, String> createRequestBodyWithAuthCode(String authCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoCredentials.getClientId());
        body.add("redirect_uri", kakaoCredentials.getRedirectUri());
        body.add("client_secret", kakaoCredentials.getClientSecret());
        body.add("code", authCode);
        return new LinkedMultiValueMap<>(body);
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
            throw new AuthException.ResourceNotFound("카카오 토큰을 가져오는 데 실패했습니다.", e);
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
            throw new AuthException.ResourceNotFound("카카오 사용자 정보를 가져오는 데 실패했습니다.", e);
        }
    }

}
