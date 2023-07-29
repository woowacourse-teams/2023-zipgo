package zipgo.auth.exception;

public class AuthException extends RuntimeException {

    public AuthException(Throwable e) {
        super(e);
    }

    public AuthException(String message) {
        super(message);
    }

}
