package zipgo.review.domain.repository.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.review.application.SortBy;
import zipgo.review.exception.NoPetFoodIdException;
import zipgo.review.exception.NonPositiveSizeException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FindReviewsFilterRequestTest {

    @Test
    void 식품_id가_null이면_예외가_발생한다() {
        // expect
        assertThatThrownBy(
                () -> FindReviewsFilterRequest.builder()
                        .petFoodId(null)
                        .size(10)
                        .lastReviewId(null)
                        .sortBy(null)
                        .build())
                .isInstanceOf(NoPetFoodIdException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, Integer.MAX_VALUE + 1})
    void size가_0_이하면_예외가_발생한다(int 음수) {

        // expect
        assertThatThrownBy(
                () -> FindReviewsFilterRequest.builder()
                        .petFoodId(1L)
                        .size(음수)
                        .lastReviewId(null)
                        .sortBy(SortBy.RECENT)
                        .build())
                .isInstanceOf(NonPositiveSizeException.class);
    }

}
