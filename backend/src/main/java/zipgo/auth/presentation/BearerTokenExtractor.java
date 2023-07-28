package zipgo.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BearerTokenExtractor {

    private static final String BEARER_TYPE = "Bearer ";
    private static final String BEARER_REGEX = "^Bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";

    private BearerTokenExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        validate(authorization);
        String token = authorization.replace(BEARER_TYPE, "").trim();

        return token;
    }

    private static void validate(String authorization) {
        if (authorization == null) {
            throw new IllegalStateException("사용자 인증이 필요합니다.");
        }
        if (!authorization.matches(BEARER_REGEX)) {
            throw new IllegalStateException("유효하지 않은 인증 형식입니다.");
        }
    }

}
