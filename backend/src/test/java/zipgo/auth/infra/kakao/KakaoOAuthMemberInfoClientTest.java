package zipgo.auth.infra.kakao;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.exception.OAuthResourceNotBringException;
import zipgo.auth.infra.kakao.config.KakaoCredentials;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KakaoOAuthMemberInfoClientTest {

    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoOAuthMemberInfoClient KakaoOAuthMemberInfoClient;

    @Test
    void 사용자_정보를_가져올_수_있다() {
        // given
        var 정보_요청 = 사용자_정보_요청_생성();
        var 응답 = ResponseEntity.ok(KakaoMemberResponse.builder().build());
        when(restTemplate.exchange(USER_INFO_URI, GET, 정보_요청, KakaoMemberResponse.class))
                .thenReturn(응답);

        // when
        OAuthMemberResponse oAuthMemberResponse = KakaoOAuthMemberInfoClient.getMember("accessToken");

        // then
        assertThat(oAuthMemberResponse).isNotNull();
    }

    @Test
    void 사용자_정보_요청시_실패_응답을_받으면_예외가_발생한다() {
        // given
        var 요청 = 사용자_정보_요청_생성();
        when(restTemplate.exchange(
                USER_INFO_URI,
                GET,
                요청,
                KakaoMemberResponse.class
        )).thenThrow(HttpClientErrorException.class);

        // expect
        assertThatThrownBy(() -> KakaoOAuthMemberInfoClient.getMember("accessToken"))
                .isInstanceOf(OAuthResourceNotBringException.class)
                .hasMessageContaining("서드파티 서비스에서 정보를 받아오지 못했습니다. 잠시후 다시 시도해주세요.");
    }


    private HttpEntity<HttpHeaders> 사용자_정보_요청_생성() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("accessToken");
        return new HttpEntity<>(headers);
    }

}
