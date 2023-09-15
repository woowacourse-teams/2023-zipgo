package zipgo.petfood.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class FunctionalityNotFoundException extends ZipgoException {

    public FunctionalityNotFoundException(Long id) {
        super(new ErrorCode(NOT_FOUND, format("아이디가 %d인 기능성을 찾을 수 없습니다.", id)));
    }

}
