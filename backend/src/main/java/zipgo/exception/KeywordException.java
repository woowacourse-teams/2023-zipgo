package zipgo.exception;

public class KeywordException extends RuntimeException {

    public KeywordException(String message) {
        super(message);
    }

    public static class NotFound extends KeywordException {

        public NotFound(String keywordName) {
            super("이름이 " + keywordName + "인 키워드를 찾을 수 없습니다.");
        }

    }

}
