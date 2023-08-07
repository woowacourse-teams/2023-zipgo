package zipgo.petfood.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.review.domain.Review;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReviewsTest {

    @ParameterizedTest
    @MethodSource("별점_리스트_만들기")
    void 식품의_평균_별점을_계산할_수_있다(List<Integer> 별점_리스트, double 예상_결과) {
        // given
        Reviews 리뷰리스트 = 별점으로_리뷰만들기(별점_리스트);

        // when
        double 계산_결과 = 리뷰리스트.calculateRatingAverage();

        // then
        assertThat(계산_결과).isEqualTo(예상_결과);
    }

    private Reviews 별점으로_리뷰만들기(final List<Integer> 별점_리스트) {
        List<Review> 리뷰 = 별점_리스트.stream()
                .map(별점 -> Review.builder().rating(별점).build())
                .toList();

        return Reviews.builder().reviews(리뷰).build();
    }

    static Stream<Arguments> 별점_리스트_만들기() {
        return Stream.of(
                Arguments.of(emptyList(), 0),
                Arguments.of(List.of(1, 2, 3, 4, 5), 3.0),
                Arguments.of(List.of(2, 3, 3, 5, 5), 3.6),
                Arguments.of(List.of(1, 2, 4), 2.3),
                Arguments.of(List.of(4, 4, 4, 5, 5), 4.4),
                Arguments.of(List.of(1, 2, 5), 2.7)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 100, 3, 2})
    void 리뷰_개수_가져오기(int 리뷰_개수) {
        //given
        List<Review> 리뷰 = new ArrayList<>();
        for (int count = 0; count < 리뷰_개수; count++) {
            리뷰.add(Review.builder().build());
        }

        Reviews 리뷰리스트 = Reviews.builder().reviews(리뷰).build();

        //when
        int 결과 = 리뷰리스트.countReviews();

        //then
        assertThat(결과).isEqualTo(리뷰_개수);
    }

}
