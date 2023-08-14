package zipgo.review.domain.repository.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.review.application.SortBy;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FindReviewsQueryRequestTest {

    @Test
    void 식품_id가_null이면_예외가_발생한다() {
        // expect
        assertThatThrownBy(
                () -> new FindReviewsQueryRequest(null, 10, null, null, emptyList(), emptyList())).isInstanceOf(
                IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, Integer.MAX_VALUE + 1})
    void size가_0_이하면_예외가_발생한다(int 음수) {

        // expect
        assertThatThrownBy(() -> new FindReviewsQueryRequest(1L, 음수, null, SortBy.RECENT, emptyList(), emptyList()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
