package zipgo.review.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.member.exception.MemberException;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.exception.StoolConditionException;
import zipgo.review.exception.TastePreferenceException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.review.domain.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.리뷰_생성_요청;

class ReviewServiceTest extends ServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewService reviewService;

    @Test
    void 리뷰를_생성할_수_있다() {
        //given
        PetFood 식품 = 키워드_없이_식품_초기화();
        Member 멤버 = memberRepository.save(무민());
        PetFood 저장된_식품 = petFoodRepository.save(식품);

        //when
        reviewService.createReview(멤버.getId(), 리뷰_생성_요청(저장된_식품.getId()));

        //then
        List<Review> reviews = reviewRepository.findAll();
        Review review = reviews.get(0);
        assertAll(
                () -> assertThat(reviews).hasSize(1),
                () -> assertThat(review.getRating()).isEqualTo(5),
                () -> assertThat(review.getComment()).isEqualTo("우리 아이랑 너무 잘 맞아요!"),
                () -> assertThat(review.getTastePreference()).isEqualTo(EATS_VERY_WELL),
                () -> assertThat(review.getStoolCondition()).isEqualTo(SOFT_MOIST),
                () -> assertThat(review.getAdverseReactions()).isEmpty()
        );
    }

    @Test
    void 잘못된_기호성으로_리뷰를_생성할_시_예외_처리() {
        //given
        PetFood 식품 = 키워드_없이_식품_초기화();
        Member 멤버 = memberRepository.save(무민());
        PetFood 저장된_식품 = petFoodRepository.save(식품);
        CreateReviewRequest request = new CreateReviewRequest(
                저장된_식품.getId(),
                5,
                "우리 아이랑 너무 잘 맞아요!",
                "어쩔 TV",
                "촉촉 말랑해요",
                new ArrayList<>()
        );

        //when
        //then
        assertThatThrownBy(() -> reviewService.createReview(멤버.getId(), request))
                .isInstanceOf(TastePreferenceException.NotFound.class);
    }

    @Test
    void 잘못된_배변_반응으로_리뷰를_생성할_시_예외_처리() {
        //given
        PetFood 식품 = 키워드_없이_식품_초기화();
        Member 멤버 = memberRepository.save(무민());
        PetFood 저장된_식품 = petFoodRepository.save(식품);
        CreateReviewRequest request = new CreateReviewRequest(
                저장된_식품.getId(),
                5,
                "우리 아이랑 너무 잘 맞아요!",
                "정말 잘 먹어요",
                "어쩔 TV",
                new ArrayList<>()
        );

        //when
        //then
        assertThatThrownBy(() -> reviewService.createReview(멤버.getId(), request))
                .isInstanceOf(StoolConditionException.NotFound.class);
    }

    @Test
    void 잘못된_멤버_아이디로_리뷰를_생성할_시_예외_처리() {
        //given
        PetFood 식품 = 키워드_없이_식품_초기화();
        PetFood 저장된_식품 = petFoodRepository.save(식품);

        //when
        //then
        assertThatThrownBy(() -> reviewService.createReview(1004L, 리뷰_생성_요청(저장된_식품.getId())))
                .isInstanceOf(MemberException.NotFound.class);
    }

}
