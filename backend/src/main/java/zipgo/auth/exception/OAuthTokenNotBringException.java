package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

public class OAuthTokenNotBringException extends ZipgoException {

    public OAuthTokenNotBringException() {
        super(new ErrorCode(BAD_GATEWAY, "서드파티 서비스에서 토큰을 받아오지 못했습니다. 잠시후 다시 시도해주세요."));
    }

}
