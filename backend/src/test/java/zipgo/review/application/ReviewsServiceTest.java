package zipgo.review.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.member.exception.MemberNotFoundException;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.fixture.BreedsFixture;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.HelpfulReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.exception.ReviewException;
import zipgo.review.exception.StoolConditionException;
import zipgo.review.exception.TastePreferenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.review.domain.type.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.type.StoolCondition.UNCERTAIN;
import static zipgo.review.domain.type.TastePreference.EATS_MODERATELY;
import static zipgo.review.domain.type.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.AdverseReactionFixture.눈물_이상반응;
import static zipgo.review.fixture.AdverseReactionFixture.먹고_토_이상반응;
import static zipgo.review.fixture.MemberFixture.멤버_이름;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.리뷰_생성_요청;
import static zipgo.review.fixture.ReviewFixture.리뷰_수정_요청;
import static zipgo.review.fixture.ReviewFixture.혹평_리뷰_생성;

class ReviewsServiceTest extends ServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ReviewService reviewService;

    @PersistenceContext
    private EntityManager entityManager;

    private Brand 브랜드;
    private PetFood 식품;

    @BeforeEach
    void setUp() {
        브랜드 = brandRepository.save(아카나_식품_브랜드_생성());
        식품 = 모든_영양기준_만족_식품(브랜드);
    }

    @Test
    void 리뷰를_생성할_수_있다() {
        //given
        Member 멤버 = memberRepository.save(무민());
        PetFood 저장된_식품 = petFoodRepository.save(식품);
        PetSize 크기 = petSizeRepository.save(소형견());
        petRepository.save(반려동물(멤버, breedsRepository.save(BreedsFixture.견종(크기))));

        //when
        reviewService.createReview(멤버.getId(), 리뷰_생성_요청(저장된_식품.getId()));

        //then
        List<Review> reviews = reviewRepository.findAll();
        Review review = reviews.get(0);
        assertAll(
                () -> assertThat(reviews).hasSize(1),
                () -> assertThat(review.getRating()).isEqualTo(5),
                () -> assertThat(review.getComment()).isEqualTo("우리 아이랑 너무 잘 맞아요!"),
                () -> assertThat(review.getReviewPetInfo().getTastePreference()).isEqualTo(EATS_VERY_WELL),
                () -> assertThat(review.getReviewPetInfo().getStoolCondition()).isEqualTo(SOFT_MOIST),
                () -> assertThat(review.getAdverseReactions()).isEmpty()
        );
    }

    @Test
    void 잘못된_기호성으로_리뷰를_생성할_시_예외_처리() {
        //given
        Member 멤버 = memberRepository.save(무민());
        PetSize 크기 = petSizeRepository.save(소형견());
        Pet 반려동물 = 반려동물(멤버, breedsRepository.save(견종(크기)));
        petRepository.save(반려동물);

        PetFood 저장된_식품 = petFoodRepository.save(식품);
        CreateReviewRequest request = new CreateReviewRequest(
                저장된_식품.getId(),
                5,
                "우리 아이랑 너무 잘 맞아요!",
                "어쩔 TV",
                "촉촉 말랑해요",
                new ArrayList<>()
        );

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(멤버.getId(), request))
                .isInstanceOf(TastePreferenceException.NotFound.class);
    }

    @Test
    void 잘못된_배변_반응으로_리뷰를_생성할_시_예외_처리() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 멤버 = memberRepository.save(무민());
        PetSize 크기 = petSizeRepository.save(소형견());
        petRepository.save(반려동물(멤버, breedsRepository.save(BreedsFixture.견종(크기))));
        PetFood 저장된_식품 = petFoodRepository.save(식품);
        CreateReviewRequest request = new CreateReviewRequest(
                저장된_식품.getId(),
                5,
                "우리 아이랑 너무 잘 맞아요!",
                "정말 잘 먹어요",
                "어쩔 TV",
                new ArrayList<>()
        );

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(멤버.getId(), request))
                .isInstanceOf(StoolConditionException.NotFound.class);
    }

    @Test
    void 잘못된_멤버_아이디로_리뷰를_생성할_시_예외_처리() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        PetFood 저장된_식품 = petFoodRepository.save(식품);

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(1004L, 리뷰_생성_요청(저장된_식품.getId())))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원을 찾을 수 없습니다. 알맞은 회원인지 확인해주세요.");
    }

    @Test
    void 리뷰를_수정할_수_있다() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 멤버 = memberRepository.save(무민());

        petFoodRepository.save(식품);
        Review 리뷰 = 혹평리뷰(식품, 멤버);

        //when
        reviewService.updateReview(멤버.getId(), 리뷰.getId(), 리뷰_수정_요청());

        //then
        Review 저장된_리뷰 = reviewRepository.getById(리뷰.getId());
        assertAll(
                () -> assertThat(저장된_리뷰.getRating()).isEqualTo(4),
                () -> assertThat(저장된_리뷰.getComment()).isEqualTo("change comment"),
                () -> assertThat(저장된_리뷰.getReviewPetInfo().getTastePreference()).isEqualTo(EATS_MODERATELY),
                () -> assertThat(저장된_리뷰.getReviewPetInfo().getStoolCondition()).isEqualTo(UNCERTAIN),
                () -> assertThat(저장된_리뷰.getAdverseReactions()).hasSize(1)
        );
    }

    private Review 혹평리뷰(PetFood 식품, Member 멤버) {
        PetSize 사이즈 = petSizeRepository.save(소형견());
        Breeds 종류 = breedsRepository.save(견종(사이즈));
        Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
        return reviewRepository.save(혹평_리뷰_생성(반려동물, 식품,
                List.of(눈물_이상반응().getAdverseReactionType().getDescription(),
                        먹고_토_이상반응().getAdverseReactionType().getDescription())));
    }


    @Test
    void 잘못된_리뷰_id로_리뷰를_수정하면_예외_처리() {
        //given
        long 잘못된_리뷰_id = 123456789L;
        모든_영양기준_만족_식품(브랜드);
        Member 멤버 = memberRepository.save(무민());

        //when, then
        assertThatThrownBy(() -> reviewService.updateReview(멤버.getId(), 잘못된_리뷰_id, 리뷰_수정_요청()))
                .isInstanceOf(ReviewException.NotFound.class);
    }

    @Test
    void 리뷰를_삭제할_수_있다() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 멤버 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 리뷰 = 혹평리뷰(식품, 멤버);

        //when
        reviewService.deleteReview(멤버.getId(), 리뷰.getId());

        //then
        assertThatThrownBy(() -> reviewRepository.getById(리뷰.getId()))
                .isInstanceOf(ReviewException.NotFound.class);
    }

    @Test
    void 도움이_돼요를_추가할_수_있다() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 작성자 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 리뷰 = 혹평리뷰(식품, 작성자);
        Member 다른회원 = memberRepository.save(멤버_이름("무지"));

        //when
        reviewService.addHelpfulReaction(다른회원.getId(), 리뷰.getId());

        //then
        Review 저장된_리뷰 = reviewRepository.getById(리뷰.getId());
        assertThat(저장된_리뷰.getHelpfulReactions())
                .extracting(HelpfulReaction::getMadeBy)
                .anyMatch(member -> member.equals(다른회원));
    }

    @Test
    void 도움이_돼요를_취소할_수_있다() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 작성자 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 리뷰 = 혹평리뷰(식품, 작성자);

        Member 다른회원 = memberRepository.save(멤버_이름("무지"));
        리뷰.reactedBy(다른회원);
        reviewRepository.save(리뷰);

        //when
        reviewService.removeHelpfulReaction(다른회원.getId(), 리뷰.getId());

        //then
        entityManager.flush();
        Review 저장된_리뷰 = reviewRepository.getById(리뷰.getId());
        assertThat(저장된_리뷰.getHelpfulReactions())
                .extracting(HelpfulReaction::getMadeBy)
                .noneMatch(member -> member.equals(다른회원));
    }

    @Test
    void 누른적없을때_도움이_돼요를_취소할_수_있다() {
        //given
        PetFood 식품 = 모든_영양기준_만족_식품(브랜드);
        Member 작성자 = memberRepository.save(무민());
        petFoodRepository.save(식품);
        Review 리뷰 = 혹평리뷰(식품, 작성자);

        Member 다른회원 = memberRepository.save(멤버_이름("무지"));

        //when
        reviewService.removeHelpfulReaction(다른회원.getId(), 리뷰.getId());

        //then
        Review 저장된_리뷰 = reviewRepository.getById(리뷰.getId());
        assertThat(저장된_리뷰.getHelpfulReactions())
                .extracting(HelpfulReaction::getMadeBy)
                .noneMatch(member -> member.equals(다른회원));
    }

}
