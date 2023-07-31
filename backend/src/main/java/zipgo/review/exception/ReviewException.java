package zipgo.review.exception;

import static java.lang.String.format;

public class ReviewException extends RuntimeException {

    public ReviewException(String message) {
        super(message);
    }

    public static class NotFound extends ReviewException {

        public NotFound(Long id) {
            super(format("아이디가 %d 인 리뷰을 찾을 수 없습니다.", id));
        }

    }

}
