package zipgo.auth.exception;

public class AuthException extends RuntimeException {

    public static class KakaoNotFound extends AuthException {

        public KakaoNotFound(String message) {
            super(message);
        }

    }

    public AuthException(Throwable e) {
        super(e);
    }

    public AuthException(String message) {
        super(message);
    }

}
