package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class TokenExpiredException extends ZipgoException {

    public TokenExpiredException() {
        super(new ErrorCode(UNAUTHORIZED, "만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요."));
    }

}
