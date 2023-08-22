package zipgo.review.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class NonPositiveSizeException extends ZipgoException {

    public NonPositiveSizeException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "size 는 양수 이어야 합니다."));
    }

}
