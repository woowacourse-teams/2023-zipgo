package zipgo.auth.support;

import java.time.Duration;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import zipgo.common.config.JwtCredentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RefreshTokenCookieProviderTest {

    private static final JwtCredentials testJwtCredentials = new JwtCredentials(
            "집사의고민시크릿키",
            1000000000L,
            60 * 60 * 24 * 7 * 2 // 일주일
    );

    @Test
    void 리프레시_토큰을_받아_쿠키를_생성한다() {
        // given
        RefreshTokenCookieProvider refreshTokenCookieProvider = new RefreshTokenCookieProvider(testJwtCredentials);

        // when
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie("refreshToken");

        // then
        assertAll(
                () -> assertThat(cookie.getPath()).isEqualTo("/"),
                () -> assertThat(cookie.isSecure()).isTrue(),
                () -> assertThat(cookie.isHttpOnly()).isTrue(),
                () -> assertThat(cookie.getMaxAge()).isEqualTo(Duration.ofMillis(60 * 60 * 24 * 7 * 2)),
                () -> assertThat(cookie.getValue()).isEqualTo("refreshToken")
        );
    }

    @Test
    void 로그아웃_쿠키를_생성한다() {
        // given
        RefreshTokenCookieProvider refreshTokenCookieProvider = new RefreshTokenCookieProvider(testJwtCredentials);

        // when
        ResponseCookie logoutCookie = refreshTokenCookieProvider.createLogoutCookie();

        // then
        assertAll(
                () -> assertThat(logoutCookie.getValue()).isEqualTo("refreshToken"),
                () -> assertThat(logoutCookie.getMaxAge()).isZero()
        );
    }

}
