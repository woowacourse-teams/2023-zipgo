package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class TokenInvalidException extends ZipgoException {

    public TokenInvalidException() {
        super(new ErrorCode(FORBIDDEN, "잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요."));
    }

}
