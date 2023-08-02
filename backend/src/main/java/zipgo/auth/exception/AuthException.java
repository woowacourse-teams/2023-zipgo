package zipgo.auth.exception;

public class AuthException extends RuntimeException {

    public static class KakaoNotFound extends AuthException {

        public KakaoNotFound(String message, Throwable cause) {
            super(message, cause);
        }

    }

    public static class Forbidden extends AuthException {

        public Forbidden() {
            super("권한이 없습니다.");
        }

    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
