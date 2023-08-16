package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.*;

public class PetAgeNotFoundSizeNotFoundException extends ZipgoException {

    public PetAgeNotFoundSizeNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 견종 크기입니다. 알맞은 견종 크기인지 확인해주세요."));
    }

}
