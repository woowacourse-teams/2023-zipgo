package zipgo.auth.support;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import zipgo.common.config.JwtCredentials;

@Component
public class RefreshTokenCookieProvider {

    public static final String REFRESH_TOKEN = "refreshToken";
    private static final String VALID_COOKIE_PATH = "/";
    private static final String LOGOUT_COOKIE_VALUE = "";
    private static final int LOGOUT_COOKIE_AGE = 0;

    private final long expirationTime;

    public RefreshTokenCookieProvider(JwtCredentials jwtCredentials) {
        this.expirationTime = jwtCredentials.getRefreshTokenExpirationTime();
    }

    public ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .maxAge(Duration.ofMillis(expirationTime))
                .path(VALID_COOKIE_PATH)
                .secure(true)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie createLogoutCookie() {
        return ResponseCookie.from(REFRESH_TOKEN, LOGOUT_COOKIE_VALUE)
                .maxAge(LOGOUT_COOKIE_AGE)
                .build();
    }

}
