package zipgo.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.brand.domain.fixture.BrandFixture.식품_브랜드_생성하기;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.review.domain.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.TastePreference.EATS_VERY_WELL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
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
    private BrandRepository brandRepository;

    @Autowired
    private MemberRepository memberRepository;


    @ParameterizedTest
    @MethodSource("별점_리스트_만들기")
    void 식품의_평균_별점을_계산할_수_있다(List<Integer> 별점_리스트, double 예상_결과) {
        // given
        PetFood 테스트_식품 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드_조회하기()));
        for (var 별점 : 별점_리스트) {
            별점으로_리뷰_만들기(별점, 테스트_식품);
        }

        // when
        double 계산_결과 = reviewService.calculateRatingAverage(테스트_식품);

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
                .tastePreference(EATS_VERY_WELL)
                .stoolCondition(SOFT_MOIST)
                .member(회원)
                .build());
    }

    private Brand 브랜드_조회하기() {
        return brandRepository.save(식품_브랜드_생성하기());
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 100, 3, 2})
    void 리뷰_개수_가져오기(int 리뷰_개수) {
        //given
        PetFood 테스트_식품 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드_조회하기()));
        리뷰_여러개_만들기(리뷰_개수, 테스트_식품);

        //when
        int 결과 = reviewService.countReviews(테스트_식품);

        //then
        assertThat(결과).isEqualTo(리뷰_개수);
    }

    private void 리뷰_여러개_만들기(int 리뷰_개수, PetFood 테스트_식품) {
        List<Review> 리뷰들 = new ArrayList<>();
        for (int count = 0; count < 리뷰_개수; count++) {
            리뷰들.add(리뷰_한개_만들기(테스트_식품));
        }
        reviewRepository.saveAll(리뷰들);
    }

    private Review 리뷰_한개_만들기(PetFood 테스트_식품) {
        Member 회원 = memberRepository.save(Member.builder().build()); // todo: 멤버 필수 필드 추가
        return Review.builder()
                .comment("잘 먹네요")
                .petFood(테스트_식품)
                .ratings(5)
                .tastePreference(EATS_VERY_WELL)
                .stoolCondition(SOFT_MOIST)
                .member(회원)
                .build();
    }

}
