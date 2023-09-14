package zipgo.review.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class InvalidPetOwnerException extends ZipgoException {

    public InvalidPetOwnerException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "요청의 반려동물의 주인이 아닙니다."));
    }

}
