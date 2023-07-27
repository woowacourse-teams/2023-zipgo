package zipgo.petfood.domain;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.review.domain.Review;

@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodTest {

    @Test
    void 아이디가_같으면_동등하다() {
        PetFood 식품_1 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        PetFood 식품_2 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        assertThat(식품_2).isEqualTo(식품_1);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        PetFood 식품_1 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        PetFood 식품_2 = PetFood.builder()
                .id(2L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        assertThat(식품_2).isNotEqualTo(식품_1);
    }

    @ParameterizedTest
    @MethodSource("별점_리스트_만들기")
    void 식품의_평균_별점을_계산할_수_있다(List<Integer> 별점_리스트, double 예상_결과) {
        // given
        List<Review> 리뷰 = 별점_리스트.stream()
                .map(별점 -> Review.builder().ratings(별점).build())
                .toList();

        PetFood 테스트_식품 = PetFood.builder()
                .reviews(리뷰)
                .build();

        // when
        double 계산_결과 = 테스트_식품.calculateRatingAverage();

        // then
        assertThat(계산_결과).isEqualTo(예상_결과);
    }

    static Stream<Arguments> 별점_리스트_만들기() {
        return Stream.of(
                Arguments.of(emptyList(), 0),
                Arguments.of(List.of(1, 2, 3, 4, 5), 3.0),
                Arguments.of(List.of(2, 3, 3, 5, 5), 3.6),
                Arguments.of(List.of(1, 2, 4), (double) 7 / 3),
                Arguments.of(List.of(4, 4, 4, 5, 5), 4.4)
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

        PetFood 테스트_식품 = PetFood.builder()
                .reviews(리뷰)
                .build();

        //when
        int 결과 = 테스트_식품.countReviews();

        //then
        assertThat(결과).isEqualTo(리뷰_개수);
    }

}
