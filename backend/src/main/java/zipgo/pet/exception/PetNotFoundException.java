package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PetNotFoundException extends ZipgoException {

    public PetNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 반려동물입니다. 다시 확인해주세요."));
    }

}
