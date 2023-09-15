package zipgo.review.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class SortByNotFoundException extends ZipgoException {

    public SortByNotFoundException() {
        super(new ErrorCode(HttpStatus.NOT_FOUND, "해당하는 정렬 기준을 찾을 수 없습니다."));
    }

}
