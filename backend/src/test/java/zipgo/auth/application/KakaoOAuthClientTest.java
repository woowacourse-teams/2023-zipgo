package zipgo.auth.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.KakaoOAuthResponse;
import zipgo.auth.OAuthResponse;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static zipgo.auth.application.KakaoOAuthClient.KAKAO_ACCESS_TOKEN_URI;
import static zipgo.auth.application.KakaoOAuthClient.KAKAO_USER_INFO_URI;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class KakaoOAuthClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoOAuthClient kakaoOAuthClient;

    @Test
    void accessToken_을_가져올_수_있다() {
        // given
        ResponseEntity<KakaoTokens> response = ResponseEntity.ok(KakaoTokens.builder().accessToken("accessToken").build());
        HttpHeaders header = kakaoOAuthClient.createRequestHeader();
        MultiValueMap<String, String> body = kakaoOAuthClient.createRequestBodyWithAuthCode("authCode");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);
        when(restTemplate.exchange(KAKAO_ACCESS_TOKEN_URI, POST, request, KakaoTokens.class))
                .thenReturn(response);

        // when
        String accessToken = kakaoOAuthClient.getAccessToken("authCode");

        // then
        assertThat(accessToken).isEqualTo("accessToken");
    }

    @Test
    void 사용자_상세_정보를_가져올_수_있다() {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("accessToken");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoOAuthResponse> response = ResponseEntity.ok(KakaoOAuthResponse.builder()
                .build());
        when(restTemplate.exchange(KAKAO_USER_INFO_URI, GET, request, KakaoOAuthResponse.class))
                .thenReturn(response);

        // when
        OAuthResponse oAuthResponse = kakaoOAuthClient.getMemberDetail("accessToken");

        // then
        assertThat(oAuthResponse).isNotNull();
    }

}
