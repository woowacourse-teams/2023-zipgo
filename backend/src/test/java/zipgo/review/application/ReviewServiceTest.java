package zipgo.review.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.StoolCondition;
import zipgo.review.domain.TastePreference;
import zipgo.review.repository.ReviewRepository;

@Transactional
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private MemberRepository memberRepository;

    @ParameterizedTest
    @MethodSource("별점_리스트_만들기")
    void 식품의_평균_별점을_계산할_수_있다(List<Integer> 별점_리스트, double 예상_결과) {
        // given
        PetFood 테스트_식품 = petFoodRepository.save(PetFoodFixture.키워드_없이_식품_초기화());
        for (var 별점 : 별점_리스트) {
            별점으로_리뷰_만들기(별점, 테스트_식품);
        }

        // when
        double 계산_결과 = reviewService.getRatingAverage(테스트_식품);

        // then
        assertThat(계산_결과).isEqualTo(예상_결과);
    }

    static Stream<Arguments> 별점_리스트_만들기() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5), 3.0),
                Arguments.of(List.of(2, 3, 3, 5, 5), 3.6),
                Arguments.of(List.of(1, 2, 4), (double) 7 / 3),
                Arguments.of(List.of(4, 4, 4, 5, 5), 4.4)
        );
    }

    private Review 별점으로_리뷰_만들기(int 별점, PetFood 테스트_식품) {
        Member 회원 = memberRepository.save(Member.builder().build()); // todo: 멤버 필수 필드 추가
        return reviewRepository.save(Review.builder()
                .comment("잘 먹네요")
                .petFood(테스트_식품)
                .ratings(별점)
                .tastePreference(TastePreference.EATS_VERY_WELL)
                .stoolCondition(StoolCondition.SOFT_MOIST)
                .member(회원)
                .build());
    }


}
