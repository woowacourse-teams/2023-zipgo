package zipgo.member.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class PetAlreadyRegisteredException extends ZipgoException {

    public PetAlreadyRegisteredException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "이미 반려동물이 있습니다."));
    }

}
