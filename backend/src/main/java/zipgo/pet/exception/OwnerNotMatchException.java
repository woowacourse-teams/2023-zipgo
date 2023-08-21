package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OwnerNotMatchException extends ZipgoException {

    public OwnerNotMatchException() {
        super(new ErrorCode(BAD_REQUEST, "반려견과 주인이 일치하지 않습니다. 다시 확인해주세요."));
    }

}
