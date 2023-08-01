package zipgo.member.exception;

public class MemberException extends RuntimeException {
    
    public MemberException(String message) {
        super(message);
    }

    public static class NotFound extends MemberException {

        public NotFound() {
            super("멤버를 찾을 수 없습니다.");
        }

    }

}
