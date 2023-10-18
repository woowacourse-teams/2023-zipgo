package zipgo.auth.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.auth.exception.TokenMissingException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZipgoTokenExtractor {

    private static final String ZIPGO_HEADER = "Refresh";
    private static final String ZIPGO_TYPE = "Zipgo ";
    private static final String ZIPGO_JWT_REGEX = "^Zipgo [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";

    public static String extract(HttpServletRequest request) {
        String authorization = request.getHeader(ZIPGO_HEADER);
        validate(authorization);
        return authorization.replace(ZIPGO_TYPE, "").trim();
    }

    private static void validate(String authorization) {
        if (authorization == null) {
            throw new TokenMissingException();
        }
        if (!authorization.matches(ZIPGO_JWT_REGEX)) {
            throw new TokenInvalidException();
        }
    }

}
