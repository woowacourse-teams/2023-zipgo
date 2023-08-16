package zipgo.pet.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PetGenderNotFoundException extends ZipgoException {

    public PetGenderNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 성별입니다. 알맞은 성별인지 확인해주세요."));
    }

}
