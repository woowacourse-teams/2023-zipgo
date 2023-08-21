package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AuthForbiddenException extends ZipgoException {

    public AuthForbiddenException() {
        super(new ErrorCode(FORBIDDEN, "권한이 없습니다. 로그인 후 이용해주세요."));
    }

}
