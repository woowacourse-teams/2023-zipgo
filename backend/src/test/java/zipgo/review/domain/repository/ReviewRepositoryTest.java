package zipgo.review.domain.repository;

import java.time.Year;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.repository.RepositoryTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.fixture.PetFixture;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.exception.ReviewException;
import zipgo.review.fixture.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;

public class ReviewRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetRepository petRepository;


    @Test
    void 도움이_돼요가_잘_추가된다() {
        //given
        Review 아는_리뷰 = 도움이_돼요를_누르고싶은_리뷰();
        Member 모르는_베베 = memberRepository.save(MemberFixture.멤버_이름("모르는_베베"));

        //when
        아는_리뷰.reactedBy(모르는_베베);
        reviewRepository.save(아는_리뷰);

        //then
        Review 조회한_리뷰 = reviewRepository.findById(아는_리뷰.getId())
                .orElseThrow(() -> new ReviewException.NotFound(아는_리뷰.getId()));
        assertThat(조회한_리뷰.getHelpfulReactions())
                .anyMatch(helpfulReaction -> helpfulReaction.getMadeBy().equals(모르는_베베));
    }

    @Test
    void 도움이_돼요_취소하기() {
        //given
        Review 아는_리뷰 = 도움이_돼요를_누르고싶은_리뷰();
        Member 모르는_베베 = memberRepository.save(MemberFixture.멤버_이름("모르는_베베"));
        아는_리뷰.reactedBy(모르는_베베);
        reviewRepository.save(아는_리뷰);

        //when
        아는_리뷰.removeReactionBy(모르는_베베);
        reviewRepository.save(아는_리뷰);

        //then
        Review 조회한_리뷰 = reviewRepository.getById(아는_리뷰.getId());
        assertThat(조회한_리뷰.getHelpfulReactions())
                .noneMatch(helpfulReaction -> helpfulReaction.getMadeBy().equals(모르는_베베));
    }

    private Review 도움이_돼요를_누르고싶은_리뷰() {
        Brand 브랜드 = brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());
        PetFood 식품 = petFoodRepository.save(PetFoodFixture.모든_영양기준_만족_식품(브랜드));
        Member 멤버 = memberRepository.save(무민());
        PetSize 사이즈 = petSizeRepository.save(소형견());
        Breeds 종류 = breedsRepository.save(견종(사이즈));
        Pet 반려동물 = petRepository.save(PetFixture.반려동물(멤버, 종류));
        return reviewRepository.save(극찬_리뷰_생성(반려동물, 식품, List.of("없어요")));
    }

    @ParameterizedTest
    @MethodSource("생년별_나이")
    void 리뷰_당시_나이를_계산할_수_있다(int 생년, int 예상_나이) {
        //given
        Review 리뷰 = 이_연도에_태어난_반려동물이_쓴_리뷰(생년);

        //when
        int 나이 = 리뷰.getPetAge();

        //then
        assertThat(나이).isEqualTo(예상_나이);
    }

    public static Stream<Arguments> 생년별_나이() {
        int 테스트_작성_연도와의_차 = Year.now().getValue() - 2023;
        return Stream.of(
                Arguments.of(1990, 33 + 테스트_작성_연도와의_차),
                Arguments.of(2020, 3 + 테스트_작성_연도와의_차),
                Arguments.of(2001, 22 + 테스트_작성_연도와의_차),
                Arguments.of(2000, 23 + 테스트_작성_연도와의_차),
                Arguments.of(2023, 0 + 테스트_작성_연도와의_차)
        );
    }

    private Review 이_연도에_태어난_반려동물이_쓴_리뷰(int 생년) {
        Brand 브랜드 = brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());
        PetFood 식품 = petFoodRepository.save(PetFoodFixture.모든_영양기준_만족_식품(브랜드));
        Member 멤버 = memberRepository.save(무민());
        PetSize 사이즈 = petSizeRepository.save(소형견());
        Breeds 종류 = breedsRepository.save(견종(사이즈));
        Pet 반려동물 = petRepository.save(
                Pet.builder()
                        .name("무민이")
                        .owner(멤버)
                        .birthYear(Year.of(생년))
                        .breeds(종류)
                        .weight(5.0)
                        .build()
        );
        Review 리뷰 = reviewRepository.save(극찬_리뷰_생성(반려동물, 식품, List.of("없어요")));
        return 리뷰;
    }

}
