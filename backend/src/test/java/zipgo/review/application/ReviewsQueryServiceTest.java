package zipgo.review.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.application.dto.GetReviewQueryRequest;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.response.GetReviewResponse;
import zipgo.review.dto.response.GetReviewsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.review.domain.type.AdverseReactionType.NONE;
import static zipgo.review.domain.type.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.type.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.AdverseReactionFixture.눈물_이상반응;
import static zipgo.review.fixture.AdverseReactionFixture.먹고_토_이상반응;
import static zipgo.review.fixture.MemberFixture.멤버_이름;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;
import static zipgo.review.fixture.ReviewFixture.혹평_리뷰_생성;

class ReviewQueryServiceTest extends QueryServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ReviewQueryService reviewQueryService;

    @Test
    void 사이즈로_리뷰_목록_조회() {
        //given
        PetFood 식품 = petFoodRepository.save(모든_영양기준_만족_식품(브랜드_조회하기()));
        Member 멤버 = memberRepository.save(무민());
        Review 리뷰1 = reviewRepository.save(극찬_리뷰_생성(멤버, 식품, List.of("없어요")));
        Member 멤버2 = memberRepository.save(멤버_이름("무민2"));
        Review 리뷰2 = reviewRepository.save(
                혹평_리뷰_생성(멤버2, 식품, List.of(눈물_이상반응().getAdverseReactionType().getDescription(),
                        먹고_토_이상반응().getAdverseReactionType().getDescription())));
        GetReviewResponse 리뷰1_dto = GetReviewResponse.from(리뷰1);
        GetReviewResponse 리뷰2_dto = GetReviewResponse.from(리뷰2);

        //when
        GetReviewsResponse reviews = reviewQueryService.getReviews(new GetReviewQueryRequest(식품.getId(), 2, null));

        //then
        assertThat(reviews.reviews().size()).isEqualTo(2);
        assertThat(reviews.reviews().get(0)).usingRecursiveComparison().isEqualTo(리뷰2_dto);
        assertThat(reviews.reviews().get(1)).usingRecursiveComparison().isEqualTo(리뷰1_dto);
    }

    @Test
    void 식품_id가_null이면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> new GetReviewQueryRequest(null, 10, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, Integer.MAX_VALUE + 1})
    void size가_0_이하면_예외가_발생한다(int 음수) {
        // expect
        assertThatThrownBy(() -> reviewQueryService.getReviews(new GetReviewQueryRequest(1L, 음수, null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Brand 브랜드_조회하기() {
        return brandRepository.save(아카나_식품_브랜드_생성());
    }

    @Test
    void getReview() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드_조회하기());
        Member 멤버 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 극찬_리뷰 = reviewRepository.save(극찬_리뷰_생성(멤버, 식품, List.of("없어요")));

        //when
        Review review = reviewQueryService.getReview(극찬_리뷰.getId());

        //then
        assertAll(
                () -> assertThat(review.getMember().getName()).isEqualTo("무민"),
                () -> assertThat(review.getRating()).isEqualTo(5),
                () -> assertThat(review.getComment()).isEqualTo("우리 아이랑 너무 잘 맞아요!"),
                () -> assertThat(review.getTastePreference()).isEqualTo(EATS_VERY_WELL),
                () -> assertThat(review.getStoolCondition()).isEqualTo(SOFT_MOIST),
                () -> assertThat(review.getAdverseReactions().get(0).getAdverseReactionType()).isEqualTo(NONE)
        );
    }

}
