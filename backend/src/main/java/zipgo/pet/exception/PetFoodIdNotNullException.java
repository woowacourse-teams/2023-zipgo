package zipgo.pet.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.*;

public class PetFoodIdNotNullException extends ZipgoException {

    public PetFoodIdNotNullException() {
        super(new ErrorCode(BAD_REQUEST, "petFoodId는 null이 될 수 없습니다."));
    }

}
