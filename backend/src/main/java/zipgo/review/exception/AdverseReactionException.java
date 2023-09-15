package zipgo.review.exception;

public class AdverseReactionException extends RuntimeException {

    public AdverseReactionException(String message) {
        super(message);
    }

    public static class NotFound extends AdverseReactionException {

        public NotFound() {
            super("해당 이름의 이상반응을 찾을 수 없습니다.");
        }

    }

}
