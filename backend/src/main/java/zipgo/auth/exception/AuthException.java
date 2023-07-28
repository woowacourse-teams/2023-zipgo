package zipgo.auth.exception;

public class AuthException extends RuntimeException {

    public AuthException(String s) {
        super(s);
    }

    public AuthException(Throwable e) {
        super(e);
    }

}
