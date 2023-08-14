package zipgo.review.domain.repository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.config.QueryDslTestConfig;
import zipgo.member.domain.Member;
import zipgo.member.domain.fixture.MemberFixture;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.application.SortBy;
import zipgo.review.domain.HelpfulReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsQueryRequest;

import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.pet.domain.AgeGroup.PUPPY;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.review.domain.type.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.type.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;


@Import(QueryDslTestConfig.class)
@DataJpaTest(properties = {"spring.sql.init.mode=never"})
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

    private PetFood 식품_만들기() {
        Brand 브랜드 = brandRepository.save(Brand.builder()
                .name("브랜드" + LocalDateTime.now())
                .imageUrl("https://intl.acana.com/wp-content/themes/acana2019/img/logo.png")
                .nation("캐나다")
                .foundedYear(1977)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build());
        PetFood 식품 = petFoodRepository.save(모든_영양기준_만족_식품(브랜드));
        return 식품;
    }

    @Nested
    @Transactional
    class 페이지네이션 {

        @Test
        void 리뷰를_원하는_개수만큼_읽을_수_있다() {
            // given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            // when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null, SortBy.RECENT, emptyList(),
                    emptyList(), emptyList());
            List<Review> 조회한_리뷰_리스트 = reviewQueryRepository.findReviewsBy(request);

            // then
            assertThat(조회한_리뷰_리스트.size()).isEqualTo(10);
        }


        private void 리뷰_여러개_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        @Test
        void 커서보다_아이디가_작은_것들만_조회한다() {
            // given
            PetFood 식품 = 식품_만들기();
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);

            // when
            long 커서 = 리뷰들.stream().map(Review::getId).sorted().toList().get(4);
            var request = new FindReviewsQueryRequest(식품.getId(), 10, 커서,
                    SortBy.RECENT, emptyList(), emptyList(), emptyList());
            List<Long> 조회한_리뷰_아이디 = reviewQueryRepository.findReviewsBy(request).stream()
                    .map(Review::getId).toList();

            // then
            assertThat(조회한_리뷰_아이디)
                    .isNotEmpty()
                    .allMatch(id -> id < 커서);
        }

        @Test
        void null_이_들어오면_기본값으로_조회한다() {
            // given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            // when
            var request = new FindReviewsQueryRequest(식품.getId(), 20, null,
                    SortBy.RECENT, emptyList(), emptyList(), emptyList());
            List<Review> 리뷰 = reviewQueryRepository.findReviewsBy(request);

            // then
            assertThat(리뷰).hasSize(20);
        }

        @Test
        void 결과가_없는경우_빈리스트를_반환한다() {
            //given
            PetFood 식품 = 식품_만들기();
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RECENT, emptyList(), Arrays.stream(AgeGroup.values()).toList(), emptyList());

            //when
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).isEmpty();
        }

        @Test
        void 마지막_리뷰_아이디가_음수일경우_빈리스트를_반환한다() {
            //given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);
            var request = new FindReviewsQueryRequest(식품.getId(), 10, -1L,
                    SortBy.RECENT, emptyList(), Arrays.stream(AgeGroup.values()).toList(), emptyList());

            //when
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).isEmpty();
        }

    }

    @Nested
    class 정렬 {

        @Test
        void 최신순() {
            //given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RECENT, emptyList(), emptyList(), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).extracting(Review::getId).isSortedAccordingTo(reverseOrder());
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
        void 별점_높은_순() {
            //given
            PetFood 식품 = 식품_만들기();
            별점_리뷰_생성(식품);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RAGING_DESC, emptyList(), emptyList(), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).extracting(Review::getRating).isSortedAccordingTo(reverseOrder());
        }

        private void 별점_리뷰_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = Review.builder().pet(반려동물).petFood(식품).comment("없어요")
                        .rating(i + 1).adverseReactions(emptyList()).tastePreference(EATS_VERY_WELL)
                        .stoolCondition(SOFT_MOIST).build();
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        @Test
        void 별점_낮은_순() {
            //given
            PetFood 식품 = 식품_만들기();
            별점_리뷰_생성(식품);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RATING_ASC, emptyList(), emptyList(), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).extracting(Review::getRating).isSorted();
        }

        @Test
        void 도움이_되는_순() {
            //given
            PetFood 식품 = 식품_만들기();
            도움이_돼요_리뷰(식품);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.HELPFUL, emptyList(), emptyList(), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).extracting(review -> review.getHelpfulReactions().size())
                    .isSortedAccordingTo(reverseOrder());

        }

        private void 도움이_돼요_리뷰(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = Review.builder().pet(반려동물).petFood(식품).comment("없어요")
                        .rating(i + 1).adverseReactions(emptyList()).tastePreference(EATS_VERY_WELL)
                        .stoolCondition(SOFT_MOIST).build();
                for (int j = 5 - i; j > 0; j--) {
                    Member 다른_멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i * j));
                    리뷰.getHelpfulReactions().add(HelpfulReaction.builder().review(리뷰).madeBy(다른_멤버).build());
                }
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

    }

    @Nested
    class 필터링 {

        @Test
        void 원하는_견종으로_필터링() {
            //given
            PetSize 사이즈 = petSizeRepository.save(소형견());
            Breeds 종류 = breedsRepository.save(견종(사이즈));
            PetFood 식품 = 식품_만들기();
            랜덤_리뷰_생성(식품);
            견종_리뷰_생성(식품, 종류);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RECENT, List.of(종류.getId()), emptyList(), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews).extracting(Review::getPet).extracting(Pet::getBreeds).containsOnly(종류);
        }

        private void 랜덤_리뷰_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = -20; i < 0; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        private void 견종_리뷰_생성(PetFood 식품, Breeds 종류) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = Review.builder().pet(반려동물).petFood(식품).comment("없어요")
                        .rating(i + 1).adverseReactions(emptyList()).tastePreference(EATS_VERY_WELL)
                        .stoolCondition(SOFT_MOIST).build();
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        @Test
        void 원하는_나이대로_필터링() {
            //given
            PetFood 식품 = 식품_만들기();
            랜덤_리뷰_생성(식품);
            나이대_리뷰_생성(식품, 2023);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RECENT, emptyList(), List.of(PUPPY), emptyList());
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews)
                    .extracting(review -> AgeGroup.from(review.getPet().calculateCurrentAge()))
                    .containsOnly(PUPPY);
        }

        private void 나이대_리뷰_생성(PetFood 식품, int 출생연도) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 4; i++) {

                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(Pet.builder().name("무민이").owner(멤버)
                        .birthYear(Year.of(출생연도)).breeds(종류).weight(5.0).build());
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        @Test
        void 원하는_사이즈로_필터링() {
            //given
            PetFood 식품 = 식품_만들기();
            랜덤_리뷰_생성(식품);
            PetSize 사이즈 = petSizeRepository.save(PetSize.builder().name("대형견").build());
            사이즈_리뷰_생성(식품, 사이즈);

            //when
            var request = new FindReviewsQueryRequest(식품.getId(), 10, null,
                    SortBy.RECENT, emptyList(), emptyList(), List.of(사이즈.getId()));
            List<Review> reviews = reviewQueryRepository.findReviewsBy(request);

            //then
            assertThat(reviews)
                    .extracting(review -> review.getPet().getBreeds().getPetSize())
                    .containsOnly(사이즈);
        }

        private void 사이즈_리뷰_생성(PetFood 식품, PetSize 사이즈) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

    }

}
