package zipgo.review.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ReviewSelfReactedException extends ZipgoException {

    public ReviewSelfReactedException() {
        super(new ErrorCode(BAD_REQUEST, "본인의 리뷰에는 도움이 돼요를 추가할 수 없습니다."));
    }

}
