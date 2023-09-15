package zipgo.review.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zipgo.review.exception.SortByNotFoundException;

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

    public static SortBy from(Long id) {
        for (SortBy sortBy : SortBy.values()) {
            if (sortBy.id.equals(id)) {
                return sortBy;
            }
        }
        throw new SortByNotFoundException();
    }

}
