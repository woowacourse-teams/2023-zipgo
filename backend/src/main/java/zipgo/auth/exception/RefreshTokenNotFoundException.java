package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RefreshTokenNotFoundException extends ZipgoException {

    public RefreshTokenNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 리프레시 토큰입니다."));
    }

}
