package zipgo.petfood.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PrimaryIngredientNotFoundException extends ZipgoException {

    public PrimaryIngredientNotFoundException(Long id) {
        super(new ErrorCode(NOT_FOUND, format("아이디가 %d인 주원료를 찾을 수 없습니다.", id)));
    }

}
