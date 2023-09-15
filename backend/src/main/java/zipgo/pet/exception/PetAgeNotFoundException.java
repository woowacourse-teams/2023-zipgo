package zipgo.pet.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class PetAgeNotFoundException extends ZipgoException {

    public PetAgeNotFoundException() {
        super(new ErrorCode(HttpStatus.NOT_FOUND, "분류에 속하지 않는 나이입니다. 알맞은 나이인지 확인해주세요."));
    }

}
