package zipgo.pet.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class NotMatchOwnerException extends ZipgoException {

    public NotMatchOwnerException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "반려견과 주인이 일치하지 않습니다. 다시 확인해주세요."));
    }

}
