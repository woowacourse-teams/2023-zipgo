package zipgo.auth.application;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.application.fixture.MemberResponseSuccessFixture;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.exception.OAuthResourceNotBringException;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.exception.TokenExpiredException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceFacadeTest {

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthServiceFacade authServiceFacade;

    @Test
    void 로그인에_성공하면_토큰을_발급한다() {
        // given
        when(oAuthClient.getAccessToken("인가 코드"))
                .thenReturn("엑세스 토큰");
        OAuthMemberResponse 서드파티_사용자_응답 = new MemberResponseSuccessFixture();
        when(oAuthClient.getMember("엑세스 토큰"))
                .thenReturn(서드파티_사용자_응답);
        when(authService.login(서드파티_사용자_응답))
                .thenReturn(TokenDto.of("생성된 엑세스 토큰", "생성된 리프레시 토큰"));

        // when
        TokenDto 토큰 = authServiceFacade.login("인가 코드");

        // then
        assertAll(
                () -> assertThat(토큰.accessToken()).isEqualTo("생성된 엑세스 토큰"),
                () -> assertThat(토큰.refreshToken()).isEqualTo("생성된 리프레시 토큰")
        );
    }

    @Test
    void 엑세스_토큰을_가져오지_못하면_예외가_발생한다() {
        // given
        when(oAuthClient.getAccessToken("인가 코드"))
                .thenThrow(new OAuthTokenNotBringException());

        // expect
        assertThatThrownBy(() -> authServiceFacade.login("인가 코드"))
                .isInstanceOf(OAuthTokenNotBringException.class)
                .hasMessageContaining("서드파티 서비스에서 토큰을 받아오지 못했습니다. 잠시후 다시 시도해주세요.");
    }

    @Test
    void 사용자_정보를_가져오지_못하면_예외가_발생한다() {
        // given
        when(oAuthClient.getAccessToken("인가 코드"))
                .thenReturn("엑세스 토큰");
        when(oAuthClient.getMember("엑세스 토큰"))
                .thenThrow(new OAuthResourceNotBringException());

        // expect
        assertThatThrownBy(() -> authServiceFacade.login("인가 코드"))
                .isInstanceOf(OAuthResourceNotBringException.class)
                .hasMessageContaining("서드파티 서비스에서 정보를 받아오지 못했습니다. 잠시후 다시 시도해주세요");
    }


    @Test
    void 엑세스_토큰을_갱신할_수_있다() {
        // given
        when(authService.renewAccessTokenBy("리프레시 토큰"))
                .thenReturn("갱신된 엑세스 토큰");
        // when
        String 리프레시_토큰 = authServiceFacade.renewAccessTokenBy("리프레시 토큰");

        // then
        assertThat(리프레시_토큰).isNotEmpty();
    }

    @Test
    void 만료된_엑세스_토큰은_갱신시_예외가_발생한다() {
        // given
        when(authService.renewAccessTokenBy("리프레시 토큰"))
                .thenThrow(new TokenExpiredException());
        // expect
        assertThatThrownBy(() -> authServiceFacade.renewAccessTokenBy("리프레시 토큰"))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessageContaining("만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void 로그아웃_할_수_있다() {
        // given
        Long memberId = 123L;

        // when
        authServiceFacade.logout(memberId);

        // then
        verify(authService, times(1)).logout(memberId);
    }

}
