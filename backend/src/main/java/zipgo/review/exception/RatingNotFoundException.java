package zipgo.review.exception;

import org.springframework.http.HttpStatus;
import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

public class RatingNotFoundException extends ZipgoException {

    public RatingNotFoundException() {
        super(new ErrorCode(HttpStatus.NOT_FOUND, "일치하는 별점을 찾을 수 없습니다. 별점이 1점~5점 사이인지 확인해주세요."));
    }

}
