package zipgo.auth.support;

import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import zipgo.common.config.JwtCredentials;

@Component
public class RefreshTokenCookieProvider {

    public static final String REFRESH_TOKEN = "refreshToken";
    private static final String VALID_COOKIE_PATH = "/";

    private final long expireLength;

    public RefreshTokenCookieProvider(JwtCredentials jwtCredentials) {
        this.expireLength = jwtCredentials.getRefreshTokenExpireLength();
    }

    public ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .maxAge(Duration.ofMillis(expireLength))
                .path(VALID_COOKIE_PATH)
                .secure(true)
                .httpOnly(true)
                .build();
    }

}
