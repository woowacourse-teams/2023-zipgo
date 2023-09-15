package zipgo.review.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class NoPetFoodIdException extends ZipgoException {

    public NoPetFoodIdException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "petFoodId는 null 이 될 수 없습니다."));
    }

}
