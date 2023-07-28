package zipgo.review.exception;

public class TastePreferenceException extends RuntimeException {

    public TastePreferenceException(String message) {
        super(message);
    }

    public static class NotFound extends TastePreferenceException {

        public NotFound() {
            super("기호성을 찾을 수 없습니다.");
        }

    }

}
