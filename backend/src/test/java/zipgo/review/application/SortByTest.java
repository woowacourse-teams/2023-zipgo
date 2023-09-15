package zipgo.review.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.review.exception.SortByNotFoundException;

class SortByTest {

    @Nested
    class 정적팩토리메서드 {

        @ParameterizedTest
        @ValueSource(longs = {0L, 5L, 100L})
        void 해당하는_아이디가_없으면_예외가_발생한다() {
            // given
            Long id = 0L;
            // expect
            Assertions.assertThatThrownBy(() -> SortBy.from(id))
                    .isInstanceOf(SortByNotFoundException.class);
        }

    }

}
