package zipgo.review.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortBy {
    RECENT(1L, "최신순"),
    RAGING_DESC(2L, "별점 높은 순"),
    RATING_ASC(3L, "별점 낮은 순"),
    HELPFUL(4L, "도움이 되는 순"),
    ;

    private final Long id;
    private final String name;

}
