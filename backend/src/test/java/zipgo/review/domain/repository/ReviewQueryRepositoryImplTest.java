package zipgo.review.domain.repository;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.config.QueryDslTestConfig;
import zipgo.member.domain.Member;
import zipgo.member.domain.fixture.MemberFixture;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;


@DataJpaTest
@Import(QueryDslTestConfig.class)
class ReviewQueryRepositoryImplTest {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetRepository petRepository;


    @Test
    void 리뷰를_원하는_개수만큼_읽을_수_있다() {
        // given
        PetFood 식품 = 식품_만들기();
        리뷰_여러개_생성(식품);

        // when
        List<Review> 조회한_리뷰_리스트 = reviewQueryRepository.findReviewsBy(식품.getId(), 10, null);

        // then
        assertThat(조회한_리뷰_리스트.size()).isEqualTo(10);
    }

    private PetFood 식품_만들기() {
        Brand 브랜드 = brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        PetFood 식품 = petFoodRepository.save(PetFoodFixture.모든_영양기준_만족_식품(브랜드));
        return 식품;
    }

    private void 리뷰_여러개_생성(PetFood 식품) {
        List<Review> 리뷰들 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
            PetSize 사이즈 = petSizeRepository.save(소형견());
            Breeds 종류 = breedsRepository.save(견종(사이즈));
            Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
            Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품,
                    List.of("없어요"));
            리뷰들.add(리뷰);
        }
        reviewRepository.saveAll(리뷰들);
    }

    @Test
    void 커서보다_아이디가_작은_것들만_조회한다() {
        // given
        PetFood 식품 = 식품_만들기();
        리뷰_여러개_생성(식품);

        // when
        long 커서 = 10L;
        List<Long> 조회한_리뷰_아이디 = reviewQueryRepository.findReviewsBy(식품.getId(), 10, 커서).stream()
                .map(Review::getId).toList();

        // then
        assertThat(조회한_리뷰_아이디).allMatch(id -> id < 커서);
    }

    @Test
    void null_이_들어오면_기본값으로_조회한다() {
        // given
        PetFood 식품 = 식품_만들기();
        리뷰_여러개_생성(식품);

        // when
        List<Review> 리뷰 = reviewQueryRepository.findReviewsBy(식품.getId(), 20, null);

        // then
        assertThat(리뷰).hasSize(20);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -1, -102948, Integer.MAX_VALUE + 1})
    void size가_0_이하면_예외를던진다() {
        // expect
        assertThatThrownBy(() -> reviewQueryRepository.findReviewsBy(1L, -1, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("size는 0보다 커야 합니다.");
    }

    @Test
    void petFoodId가_null이면_예외를던진다() {
        // expect
        assertThatThrownBy(() -> reviewQueryRepository.findReviewsBy(null, 1, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("petFoodId는 null이 될 수 없습니다.");
    }

    @Test
    void 결과가_없는경우_빈리스트를_반환한다() {
        //given
        PetFood 식품 = 식품_만들기();

        //when
        List<Review> reviews = reviewQueryRepository.findReviewsBy(식품.getId(), 10, null);

        //then
        assertThat(reviews).isEmpty();
    }

    @Test
    void 마지막_리뷰_아이디가_음수일경우_빈리스트를_반환한다() {
        //given
        PetFood 식품 = 식품_만들기();
        리뷰_여러개_생성(식품);

        //when
        List<Review> reviews = reviewQueryRepository.findReviewsBy(식품.getId(), 10, -1L);

        //then
        assertThat(reviews).isEmpty();
    }

}
