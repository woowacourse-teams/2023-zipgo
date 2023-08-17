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

    public static class SelfReacted extends ReviewException {

        public SelfReacted() {
            super("본인의 리뷰에는 도움이 돼요를 추가할 수 없습니다.");
        }

    }

}
