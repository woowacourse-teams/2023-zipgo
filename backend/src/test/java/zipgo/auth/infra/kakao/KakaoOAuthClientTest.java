package zipgo.auth.infra.kakao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.infra.kakao.config.KakaoCredentials;
import zipgo.auth.infra.kakao.dto.KakaoTokenResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KakaoOAuthClientTest {

    private static final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";

    private RestTemplate restTemplate;

    @Mock
    private KakaoOAuthTokenClient kakaoOAuthTokenClient;

    @Mock
    private KakaoOAuthMemberInfoClient kakaoOAuthMemberInfoClient;

    @InjectMocks
    private KakaoOAuthClient kakaoOAuthClient;

    @BeforeEach
    void setUp() {
        KakaoCredentials kakaoCredentials = new KakaoCredentials("clientId", "redirectUri", "clientSecret");
        restTemplate = Mockito.mock(RestTemplate.class);
        kakaoOAuthTokenClient = new KakaoOAuthTokenClient(restTemplate, kakaoCredentials);
        kakaoOAuthMemberInfoClient = new KakaoOAuthMemberInfoClient(restTemplate);
        kakaoOAuthClient = new KakaoOAuthClient(kakaoOAuthTokenClient, kakaoOAuthMemberInfoClient);
    }

    @Test
    void 토큰을_가져오는데_실패하면_예외가_발생한다() {
        // given
        var 요청 = 토큰_요청_생성();
        when(restTemplate.exchange(
                ACCESS_TOKEN_URI,
                POST,
                요청,
                KakaoTokenResponse.class
        )).thenThrow(HttpClientErrorException.class);

        assertThatThrownBy(() -> kakaoOAuthClient.request("authCode", "redirectUri"))
                .isInstanceOf(OAuthTokenNotBringException.class)
                .hasMessageContaining("서드파티 서비스에서 토큰을 받아오지 못했습니다. 잠시후 다시 시도해주세요.");
    }

    private HttpEntity<MultiValueMap<String, String>> 토큰_요청_생성() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", "clientId");
        body.add("redirect_uri", "redirectUri");
        body.add("client_secret", "clientSecret");
        body.add("code", "authCode");

        return new HttpEntity<>(body, header);
    }

}
