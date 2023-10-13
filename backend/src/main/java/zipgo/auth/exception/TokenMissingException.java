package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class TokenMissingException extends ZipgoException {

    public TokenMissingException() {
        super(new ErrorCode(UNAUTHORIZED, "토큰이 필요합니다."));
    }

}
