package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ReviewSizeNegativeException extends ZipgoException {

    public ReviewSizeNegativeException() {
        super(new ErrorCode(BAD_REQUEST, "size는 0보다 커야 합니다. size가 양수인지 확인해주세요."));
    }

}
