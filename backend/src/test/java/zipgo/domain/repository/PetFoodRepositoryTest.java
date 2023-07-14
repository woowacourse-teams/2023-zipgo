package zipgo.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.domain.fixture.PetFoodFixture.키워드가_없이_초기화된_식품;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_2;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.fixture.PetFoodFixture;

@DataJpaTest
class PetFoodRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void 모든_식품을_조회할_수_있다() {
        // given
        PetFood 저장된_식품_1 = petFoodRepository.save(키워드가_없이_초기화된_식품);
        PetFood 저장된_식품_2 = petFoodRepository.save(반려동물_식품_2);

        // when
        List<PetFood> petFoods = petFoodRepository.findAll();

        // then
        assertThat(petFoods).contains(저장된_식품_1, 저장된_식품_2);
    }

    @Test
    void 키워드로_식품을_조회할_수_있다() {
        // given
        petFoodRepository.save(키워드가_없이_초기화된_식품);

        Keyword 키워드 = keywordRepository.findAll().get(0);
        PetFood 키워드가_있는_식품 = petFoodRepository.save(new PetFood("[고집] 돌아온 배배",
                "https://github.com/woowacourse-teams/2023-zipgo",
                "https://avatars.githubusercontent.com/u/94087228?v=4", 키워드));

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(키워드);

        // then
        assertThat(조회된_식품).contains(키워드가_있는_식품);
    }

    @Test
    void 동일한_키워드가_아닌_경우_조회하지_않는다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(PetFoodFixture.키워드가_없이_초기화된_식품);

        Keyword 키워드 = keywordRepository.findAll().get(0);
        petFoodRepository.save(new PetFood("[고집] 돌아온 배배",
                "https://github.com/woowacourse-teams/2023-zipgo",
                "https://avatars.githubusercontent.com/u/94087228?v=4", 키워드));

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(키워드);

        // then
        assertThat(조회된_식품).doesNotContain(키워드가_없는_식품);
    }
}
