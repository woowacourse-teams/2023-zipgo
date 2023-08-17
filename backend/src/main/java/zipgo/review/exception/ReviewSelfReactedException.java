package zipgo.review.exception;

public class ReviewSelfReactedException extends RuntimeException { // TODO: ZipgoException 상속 받도록 변경하기

    public ReviewSelfReactedException() {
        super("본인의 리뷰에는 도움이 돼요를 추가할 수 없습니다.");
    }

}
