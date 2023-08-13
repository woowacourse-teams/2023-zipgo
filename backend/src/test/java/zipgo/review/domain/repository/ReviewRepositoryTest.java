package zipgo.review.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
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
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.HelpfulReaction;
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

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    void 도움이_돼요가_잘_추가된다() {
        //given
        Review 아는_리뷰 = 도움이_돼요를_누르고싶은_리뷰();
        Member 모르는_베베 = memberRepository.save(MemberFixture.멤버_이름("모르는_베베"));

        //when
        HelpfulReaction 도움이_돼요 = HelpfulReaction.builder()
                .review(아는_리뷰)
                .madeBy(모르는_베베)
                .build();

        아는_리뷰.getHelpfulReactions().add(도움이_돼요);
        reviewRepository.save(아는_리뷰);
        entityManager.flush();
        entityManager.clear();

        //then
        Review 조회한_리뷰 = reviewRepository.findById(아는_리뷰.getId())
                .orElseThrow(() -> new ReviewException.NotFound(아는_리뷰.getId()));
        assertThat(조회한_리뷰.getHelpfulReactions())
                .anyMatch(helpfulReaction -> helpfulReaction.getMadeBy().equals(모르는_베베));
    }

    private Review 도움이_돼요를_누르고싶은_리뷰() {
        Brand 브랜드 = brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());
        PetFood 식품 = petFoodRepository.save(PetFoodFixture.모든_영양기준_만족_식품(브랜드));
        Member 멤버 = memberRepository.save(무민());
        PetSize 사이즈 = petSizeRepository.save(소형견());
        Breeds 종류 = breedsRepository.save(견종(사이즈));
        Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
        return reviewRepository.save(극찬_리뷰_생성(반려동물, 식품, List.of("없어요")));
    }

}
