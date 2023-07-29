package zipgo.auth.exception;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

    public static class Forbidden extends AuthException {

        public Forbidden() {
            super("권한이 없습니다.");
        }

    }

}
