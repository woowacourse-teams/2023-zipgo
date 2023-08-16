package zipgo.auth.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class OAuthResourceNotBringException extends ZipgoException {

    public OAuthResourceNotBringException() {
        super(new ErrorCode(NOT_FOUND, "서드파티 서비스에서 정보를 받아오지 못했습니다. 잠시후 다시 시도해주세요."));
    }

}
