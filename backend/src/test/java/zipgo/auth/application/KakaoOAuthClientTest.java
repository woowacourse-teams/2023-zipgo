package zipgo.auth.application;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.application.dto.KakaoMemberResponse;
import zipgo.auth.application.dto.KakaoTokenResponse;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static zipgo.auth.application.KakaoOAuthClient.KAKAO_ACCESS_TOKEN_URI;
import static zipgo.auth.application.KakaoOAuthClient.KAKAO_USER_INFO_URI;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KakaoOAuthClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoOAuthClient kakaoOAuthClient;

    @Test
    void accessToken_을_가져올_수_있다() {
        // given
        var 토큰_요청 = 토큰_요청_생성();
        var 응답 = ResponseEntity.ok(
                new KakaoTokenResponse("accessToken", null, null, null, null, null)
        );
        when(restTemplate.exchange(KAKAO_ACCESS_TOKEN_URI, POST, 토큰_요청, KakaoTokenResponse.class))
                .thenReturn(응답);

        // when
        String accessToken = kakaoOAuthClient.getAccessToken("authCode");

        // then
        assertThat(accessToken).isEqualTo("accessToken");
    }

    private HttpEntity<MultiValueMap<String, String>> 토큰_요청_생성() {
        HttpHeaders header = kakaoOAuthClient.createRequestHeader();
        MultiValueMap<String, String> body = kakaoOAuthClient.createRequestBodyWithAuthCode("authCode");
        return new HttpEntity<>(body, header);
    }

    @Test
    void 사용자_상세_정보를_가져올_수_있다() {
        // given
        var 요청 = createRequestHeaderWithToken();
        var 응답 = ResponseEntity.ok(KakaoMemberResponse.builder().build());
        when(restTemplate.exchange(KAKAO_USER_INFO_URI, GET, 요청, KakaoMemberResponse.class))
                .thenReturn(응답);

        // when
        OAuthMemberResponse oAuthMemberResponse = kakaoOAuthClient.getMember("accessToken");

        // then
        assertThat(oAuthMemberResponse).isNotNull();
    }

    private HttpEntity<HttpHeaders> createRequestHeaderWithToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("accessToken");
        return new HttpEntity<>(headers);
    }

    @Nested
    class 카카오서버_실패응답 {

        @Test
        void 토큰요청시_실패응답을_받으면_예외가_발생한다() {
            // given
            var 요청 = 토큰_요청_생성();
            when(restTemplate.exchange(
                    KAKAO_ACCESS_TOKEN_URI,
                    POST,
                    요청,
                    KakaoTokenResponse.class
            )).thenThrow(HttpClientErrorException.class);

            // expect
            assertThatThrownBy(() -> kakaoOAuthClient.getAccessToken("authCode"))
                    .isInstanceOf(AuthException.KakaoNotFound.class)
                    .hasMessageContaining("카카오 토큰을 가져오는 중 에러가 발생했습니다");
        }

        @Test
        void 사용자_정보_요청시_실패응답을_받으면_예외가_발생한다() {
            // given
            var 요청 = createRequestHeaderWithToken();
            when(restTemplate.exchange(
                    KAKAO_USER_INFO_URI,
                    GET,
                    요청,
                    KakaoMemberResponse.class
            )).thenThrow(HttpClientErrorException.class);

            // expect
            assertThatThrownBy(() -> kakaoOAuthClient.getMember("accessToken"))
                    .isInstanceOf(AuthException.KakaoNotFound.class)
                    .hasMessageContaining("카카오 사용자 정보를 가져오는 중 에러가 발생");
        }

    }

}
