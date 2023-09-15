package zipgo.brand.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BrandNotFoundException extends ZipgoException {

    public BrandNotFoundException(Long id) {
        super(new ErrorCode(NOT_FOUND, format("아이디가 %d인 상품을 찾을 수 없습니다.", id)));
    }

    public BrandNotFoundException(String name) {
        super(new ErrorCode(NOT_FOUND, format("이름이 %s인 상품을 찾을 수 없습니다.", name)));
    }

}
