package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BreedsNotFoundException extends ZipgoException {

    public BreedsNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 견종입니다. 알맞은 견종인지 확인해주세요."));
    }

}
