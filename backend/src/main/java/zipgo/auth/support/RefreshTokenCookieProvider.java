package zipgo.auth.support;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import zipgo.common.config.JwtCredentials;

@Component
public class RefreshTokenCookieProvider {

    public static final String REFRESH_TOKEN = "refreshToken";
    private static final String VALID_COOKIE_PATH = "/";

    private final int expireLength;

    public RefreshTokenCookieProvider(JwtCredentials jwtCredentials) {
        this.expireLength = (int) jwtCredentials.getRefreshTokenExpireLength();
    }

    public Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(expireLength);
        cookie.setPath(VALID_COOKIE_PATH);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
