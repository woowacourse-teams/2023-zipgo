package zipgo.exception;

public class KeywordException extends RuntimeException {

    public KeywordException(String message) {
        super(message);
    }

    public static class NotFound extends KeywordException {

        public NotFound(String keywordName) {
            super(String.format("이름이 %s 인 키워드를 찾을 수 없습니다.", keywordName));
        }

    }

}
