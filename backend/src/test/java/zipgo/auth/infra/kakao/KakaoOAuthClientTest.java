package zipgo.auth.infra.kakao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.application.OAuthClient;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;
import zipgo.auth.infra.kakao.dto.KakaoTokenResponse;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.exception.AuthException;
import zipgo.auth.infra.kakao.KakaoOAuthClient;
import zipgo.common.config.KakaoCredentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static zipgo.auth.infra.kakao.KakaoOAuthClient.GRANT_TYPE;
import static zipgo.auth.infra.kakao.KakaoOAuthClient.ACCESS_TOKEN_URI;
import static zipgo.auth.infra.kakao.KakaoOAuthClient.USER_INFO_URI;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KakaoOAuthClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private KakaoCredentials kakaoCredentials;

    private OAuthClient kakaoOAuthClient;

    @BeforeEach
    void setUp() {
        when(kakaoCredentials.getClientId()).thenReturn("clientId");
        when(kakaoCredentials.getClientSecret()).thenReturn("clientSecret");
        when(kakaoCredentials.getRedirectUri()).thenReturn("redirectUri");

        kakaoOAuthClient = new KakaoOAuthClient(kakaoCredentials, restTemplate);
    }

    @Nested
    class 카카오서버_성공_응답 {

        @Test
        void accessToken_을_가져올_수_있다() {
            // given
            var 토큰_요청 = 토큰_요청_생성();
            var 응답 = ResponseEntity.ok(
                    new KakaoTokenResponse("accessToken", null, null, null, null, null)
            );
            when(restTemplate.exchange(ACCESS_TOKEN_URI, POST, 토큰_요청, KakaoTokenResponse.class))
                    .thenReturn(응답);

            // when
            String accessToken = kakaoOAuthClient.getAccessToken("authCode");

            // then
            assertThat(accessToken).isEqualTo("accessToken");
        }

        @Test
        void 사용자_정보를_가져올_수_있다() {
            // given
            var 정보_요청 = 사용자_정보_요청_생성();
            var 응답 = ResponseEntity.ok(KakaoMemberResponse.builder().build());
            when(restTemplate.exchange(USER_INFO_URI, GET, 정보_요청, KakaoMemberResponse.class))
                    .thenReturn(응답);

            // when
            OAuthMemberResponse oAuthMemberResponse = kakaoOAuthClient.getMember("accessToken");

            // then
            assertThat(oAuthMemberResponse).isNotNull();
        }
    }

    @Nested
    class 카카오서버_실패_응답 {

        @Test
        void 토큰_요청시_실패응답을_받으면_예외가_발생한다() {
            // given
            var 요청 = 토큰_요청_생성();
            when(restTemplate.exchange(
                    ACCESS_TOKEN_URI,
                    POST,
                    요청,
                    KakaoTokenResponse.class
            )).thenThrow(HttpClientErrorException.class);

            // expect
            assertThatThrownBy(() -> kakaoOAuthClient.getAccessToken("authCode"))
                    .isInstanceOf(AuthException.ResourceNotFound.class)
                    .hasMessageContaining("카카오 토큰을 가져오는 데 실패했습니다.");
        }

        @Test
        void 사용자_정보_요청시_실패응답을_받으면_예외가_발생한다() {
            // given
            var 요청 = 사용자_정보_요청_생성();
            when(restTemplate.exchange(
                    USER_INFO_URI,
                    GET,
                    요청,
                    KakaoMemberResponse.class
            )).thenThrow(HttpClientErrorException.class);

            // expect
            assertThatThrownBy(() -> kakaoOAuthClient.getMember("accessToken"))
                    .isInstanceOf(AuthException.ResourceNotFound.class)
                    .hasMessageContaining("카카오 사용자 정보를 가져오는 데 실패했습니다");
        }

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

    private HttpEntity<HttpHeaders> 사용자_정보_요청_생성() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("accessToken");
        return new HttpEntity<>(headers);
    }

}
