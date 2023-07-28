package zipgo.review.exception;

public class StoolConditionException extends RuntimeException {

    public StoolConditionException(String message) {
        super(message);
    }

    public static class NotFound extends StoolConditionException {

        public NotFound() {
            super("배변 상태를 찾을 수 없습니다.");
        }

    }

}
