package zipgo.auth.infra.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.domain.OAuthMemberInfoClient;
import zipgo.auth.exception.OAuthResourceNotBringException;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class KakaoOAuthMemberInfoClient implements OAuthMemberInfoClient {

    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

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
