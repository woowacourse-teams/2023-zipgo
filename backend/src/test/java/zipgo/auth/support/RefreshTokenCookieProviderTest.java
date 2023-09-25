package zipgo.auth.support;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import zipgo.common.config.JwtCredentials;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RefreshTokenCookieProviderTest {

    private static final JwtCredentials testJwtCredentials =  new JwtCredentials(
            "집사의고민시크릿키",
            1000000000L,
            60 * 60 * 24 * 7 // 일주일
    );

    @Test
    void 리프레시_토큰을_받아_쿠키를_생성한다() {
        // given
        var refreshTokenCookieProvider = new RefreshTokenCookieProvider(testJwtCredentials);

        // when
        Cookie cookie = refreshTokenCookieProvider.createCookie("나만의리프레시토큰");

        // then
        assertAll(
                () -> assertThat(cookie.getPath()).isEqualTo("/"),
                () -> assertThat(cookie.getSecure()).isTrue(),
                () -> assertThat(cookie.isHttpOnly()).isTrue(),
                () -> assertThat(cookie.getMaxAge()).isEqualTo(60 * 60 * 24 * 7),
                () -> assertThat(cookie.getValue()).isEqualTo("나만의리프레시토큰")
        );
    }

}
