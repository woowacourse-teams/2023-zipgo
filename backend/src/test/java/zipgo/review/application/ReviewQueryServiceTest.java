package zipgo.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.식품_브랜드_생성하기;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.review.domain.type.AdverseReactionType.NONE;
import static zipgo.review.domain.type.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.type.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.AdverseReactionFixture.눈물_이상반응;
import static zipgo.review.fixture.AdverseReactionFixture.먹고_토_이상반응;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;
import static zipgo.review.fixture.ReviewFixture.혹평_리뷰_생성;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;

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
    void getAllReviews() {
        //given
        PetFood 식품 = 키워드_없이_식품_초기화(브랜드_조회하기());
        Member 멤버 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 극찬_리뷰 = reviewRepository.save(극찬_리뷰_생성(멤버, 식품));
        Review 혹평_리뷰_생성 = 혹평_리뷰_생성(멤버, 식품, List.of(눈물_이상반응().getAdverseReactionType().getDescription(), 먹고_토_이상반응().getAdverseReactionType().getDescription()));
        reviewRepository.save(혹평_리뷰_생성);

        //when
        List<Review> reviews = reviewQueryService.getAllReviews(식품.getId());
        Review 찾은_리뷰 = reviewRepository.findById(극찬_리뷰.getId()).get();

        //then
        assertAll(
                () -> assertThat(reviews.size()).isEqualTo(2),
                () -> assertThat(찾은_리뷰.getMember().getName()).isEqualTo("무민"),
                () -> assertThat(찾은_리뷰.getRating()).isEqualTo(5),
                () -> assertThat(찾은_리뷰.getComment()).isEqualTo("우리 아이랑 너무 잘 맞아요!"),
                () -> assertThat(찾은_리뷰.getTastePreference()).isEqualTo(EATS_VERY_WELL),
                () -> assertThat(찾은_리뷰.getStoolCondition()).isEqualTo(SOFT_MOIST),
                () -> assertThat(찾은_리뷰.getAdverseReactions().get(0).getAdverseReactionType()).isEqualTo(NONE)
        );
    }

    private Brand 브랜드_조회하기() {
        return brandRepository.save(식품_브랜드_생성하기());
    }

}
