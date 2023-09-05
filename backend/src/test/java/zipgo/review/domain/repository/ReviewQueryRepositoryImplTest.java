package zipgo.review.domain.repository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import zipgo.pet.domain.fixture.PetFixture;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.application.SortBy;
import zipgo.review.domain.HelpfulReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.domain.type.AdverseReactionType;

import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.pet.domain.AgeGroup.PUPPY;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.review.domain.type.AdverseReactionType.FRIZZY_FUR;
import static zipgo.review.domain.type.AdverseReactionType.NONE;
import static zipgo.review.domain.type.AdverseReactionType.SCRATCHING;
import static zipgo.review.domain.type.AdverseReactionType.TEARS;
import static zipgo.review.domain.type.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.type.TastePreference.EATS_VERY_WELL;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;

;


@Import(QueryDslTestConfig.class)
@DataJpaTest(properties = {"spring.sql.init.mode=never"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    private void 리뷰_여러개_생성(PetFood 식품) {
        List<Review> 리뷰들 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
            PetSize 사이즈 = petSizeRepository.save(소형견());
            Breeds 종류 = breedsRepository.save(견종(사이즈));
            Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
            Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
            리뷰들.add(리뷰);
        }
        reviewRepository.saveAll(리뷰들);
    }

    @Nested
    @Transactional
    class 리뷰목록_페이지네이션 {

        @Test
        void 리뷰를_원하는_개수만큼_읽을_수_있다() {
            // given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            // when
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .lastReviewId(null)
                    .sortBy(SortBy.RECENT)
                    .memberId(1L)
                    .build();

            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            // then
            assertThat(리뷰_리스트.size()).isEqualTo(10);
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
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);

            // when
            long 중간에있는_리뷰의_id = 리뷰들.stream().map(Review::getId).sorted().toList().get(4);
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .lastReviewId(중간에있는_리뷰의_id)
                    .sortBy(SortBy.RECENT)
                    .build();

            List<Long> 리뷰_아이디_리스트 = reviewQueryRepository.findReviewsBy(요청).stream()
                    .map(FindReviewsQueryResponse::id).toList();

            // then
            assertThat(리뷰_아이디_리스트)
                    .isNotEmpty()
                    .allMatch(id -> id < 중간에있는_리뷰의_id);
        }

        @Test
        void null_이_들어오면_기본값으로_조회한다() {
            // given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            // when
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(20)
                    .sortBy(SortBy.RECENT)
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            // then
            assertThat(리뷰_리스트).hasSize(20);
        }

        @Test
        void 결과가_없는경우_빈리스트를_반환한다() {
            //given
            PetFood 식품 = 식품_만들기();
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RECENT)
                    .ageGroups(Arrays.stream(AgeGroup.values()).toList())
                    .build();
            //when
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트).isEmpty();
        }

        @Test
        void 마지막_리뷰_아이디가_음수일경우_빈리스트를_반환한다() {
            //given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .lastReviewId(-1L)
                    .sortBy(SortBy.RECENT)
                    .ageGroups(Arrays.stream(AgeGroup.values()).toList())
                    .build();
            //when
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트).isEmpty();
        }

    }

    @Nested
    class 리뷰목록_정렬 {

        @Test
        void 최신순() {
            //given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);

            //when
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RECENT)
                    .build();

            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트).extracting(FindReviewsQueryResponse::id)
                    .isSortedAccordingTo(reverseOrder());
        }

        private void 리뷰_여러개_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
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
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RAGING_DESC)
                    .build();

            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트).extracting(FindReviewsQueryResponse::rating)
                    .isSortedAccordingTo(reverseOrder());
        }

        private void 별점_리뷰_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
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
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RATING_ASC)
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트)
                    .extracting(FindReviewsQueryResponse::rating)
                    .isSorted();
        }

        @Test
        void 도움이_되는_순() {
            //given
            PetFood 식품 = 식품_만들기();
            아이디_내림차순으로_도움이_돼요_눌러주기(식품);

            //when
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.HELPFUL)
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트)
                    .extracting(FindReviewsQueryResponse::id)
                    .isSorted();

        }

        private void 아이디_내림차순으로_도움이_돼요_눌러주기(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
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

        @Test
        void 도움이_돼요_없어도_조회된다() {
            //given
            PetFood 식품 = 식품_만들기();
            리뷰_여러개_생성(식품);
            아이디_내림차순으로_도움이_돼요_눌러주기(식품);

            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.HELPFUL)
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            assertThat(리뷰_리스트)
                    .hasSize(10);
        }

    }

    @Nested
    class 리뷰목록_필터링 {

        @Test
        void 원하는_견종으로_필터링() {
            //given
            PetSize 사이즈 = petSizeRepository.save(소형견());
            Breeds 종류 = breedsRepository.save(견종(사이즈));
            PetFood 식품 = 식품_만들기();
            랜덤_리뷰_생성(식품);
            견종_리뷰_생성(식품, 종류);

            //when
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RECENT)
                    .breedIds(List.of(종류.getId()))
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트)
                    .extracting(FindReviewsQueryResponse::breedId)
                    .containsOnly(종류.getId());
        }

        private void 랜덤_리뷰_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = -20; i < 0; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        private void 견종_리뷰_생성(PetFood 식품, Breeds 종류) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
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
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RECENT)
                    .ageGroups(List.of(PUPPY))
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트)
                    .extracting(review -> AgeGroup.from(Year.now().getValue() - review.petBirthYear().getValue()))
                    .containsOnly(PUPPY);
        }

        private void 나이대_리뷰_생성(PetFood 식품, int 출생연도) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 4; i++) {

                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(Pet.builder().name("무민이").owner(멤버)
                        .imageUrl("https://image.zipgo.pet/dev/pet-image/dog_icon.svg")
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
            var 요청 = FindReviewsFilterRequest.builder()
                    .petFoodId(식품.getId())
                    .size(10)
                    .sortBy(SortBy.RECENT)
                    .petSizes(List.of(사이즈.getId()))
                    .build();
            var 리뷰_리스트 = reviewQueryRepository.findReviewsBy(요청);

            //then
            assertThat(리뷰_리스트)
                    .extracting(review -> review.petSizeId())
                    .containsOnly(사이즈.getId());
        }

        private void 사이즈_리뷰_생성(PetFood 식품, PetSize 사이즈) {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

    }

    @Nested
    class 리뷰_아이디리스트로_이상반응_조회 {

        @Test
        void 저장한_이상반응이_잘_나온다() {
            //given
            PetFood 식품 = 식품_만들기();
            List<Review> 리뷰목록_생성된순서 = 리뷰_여러개_생성(식품);

            //when
            List<Review> 조회한_리뷰목록 = reviewQueryRepository.findReviewWithAdverseReactions(
                    리뷰목록_생성된순서.stream().map(Review::getId).toList());

            //then
            assertThat(조회한_리뷰목록)
                    .allMatch(review ->
                            review.getAdverseReactions().stream()
                                    .map(reaction -> reaction.getAdverseReactionType().getDescription())
                                    .toList()
                                    .containsAll(생성시_적었던_이상반응들(리뷰목록_생성된순서, review))
                    );

        }

        private List<String> 생성시_적었던_이상반응들(List<Review> 리뷰목록_생성된순서, Review 조회한_리뷰목록_중_하나) {
            return 리뷰목록_생성된순서.stream()
                    .filter(리뷰 -> 리뷰.getId().equals(조회한_리뷰목록_중_하나.getId()))
                    .map(리뷰 -> 리뷰.getAdverseReactions().stream()
                            .map(이상반응 -> 이상반응.getAdverseReactionType().getDescription())
                            .toList())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("아이디로 리뷰를 찾을 수가 없음"));
        }

        private List<Review> 리뷰_여러개_생성(PetFood 식품) {
            List<Review> 리뷰들 = new ArrayList<>();
            List<AdverseReactionType> 이상반응 = List.of(NONE, TEARS, SCRATCHING, FRIZZY_FUR);
            for (int i = 0; i < 20; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
                AdverseReactionType 랜덤_이상반응 = 이상반응.get(i % 4);
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품, List.of(랜덤_이상반응.getDescription()));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
            return 리뷰들;
        }

    }

    @Nested
    class 리뷰_아이디리스트로_도움이돼요_조회 {

        @Test
        void 내가_도움이_돼요를_누른리뷰_조회() {
            //given
            PetFood 식품 = 식품_만들기();
            Member 조회_요청_주체 = memberRepository.save(MemberFixture.식별자_없는_멤버("계속 도움을 얻는 사람"));
            List<Long> 도움이돼요를_누른_리뷰_아이디 = 조회_생성_후_도움이돼요_누르기(식품, 조회_요청_주체);

            //when
            var 리뷰_목록 = reviewQueryRepository.findReviewWithHelpfulReactions(
                    도움이돼요를_누른_리뷰_아이디, 조회_요청_주체.getId());

            //then
            assertThat(리뷰_목록)
                    .extracting(ReviewHelpfulReaction::reacted)
                    .allMatch(reacted -> reacted);
        }

        private List<Long> 조회_생성_후_도움이돼요_누르기(PetFood 식품, Member 도움이돼요_누르는_사람) {
            List<Review> 도움이_돼요_누른_리뷰 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
                Review 리뷰 = Review.builder().pet(반려동물).petFood(식품).comment("없어요")
                        .rating(i + 1).adverseReactions(emptyList()).tastePreference(EATS_VERY_WELL)
                        .stoolCondition(SOFT_MOIST).build();
                reviewRepository.save(리뷰);
                if (new Random().nextInt() % 2 == 0) {
                    리뷰.getHelpfulReactions().add(HelpfulReaction.builder().review(리뷰).madeBy(도움이돼요_누르는_사람).build());
                    reviewRepository.save(리뷰);
                    도움이_돼요_누른_리뷰.add(리뷰);
                }
            }
            return 도움이_돼요_누른_리뷰.stream().map(Review::getId).toList();
        }

    }


}
