package zipgo.pet.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class AgeGroupNotFoundException extends ZipgoException {

    public AgeGroupNotFoundException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "해당하는 연령대를 찾을 수 없습니다."));
    }

}
