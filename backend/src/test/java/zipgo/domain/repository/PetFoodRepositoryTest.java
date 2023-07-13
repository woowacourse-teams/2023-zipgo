package zipgo.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.domain.fixture.PetFoodFixture.다이어트_키워드_반려동물_식품;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_1;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_2;
import static zipgo.domain.fixture.PetFoodFixture.키워드가_없는_반려동물_식품;

import java.util.List;
import org.junit.jupiter.api.Test;
import zipgo.domain.PetFood;
import zipgo.domain.repository.fake.PetFoodFakeRepository;

class PetFoodRepositoryTest {

    private PetFoodRepository petFoodRepository = new PetFoodFakeRepository();

    @Test
    void 모든_식품을_조회할_수_있다() {
        // given
        petFoodRepository.save(반려동물_식품_1);
        petFoodRepository.save(반려동물_식품_2);

        // when
        List<PetFood> petFoods = petFoodRepository.findAll();

        // then
        assertThat(petFoods).contains(반려동물_식품_1, 반려동물_식품_2);
    }

    @Test
    void 키워드로_식품을_조회할_수_있다() {
        // given
        petFoodRepository.save(반려동물_식품_1);
        petFoodRepository.save(반려동물_식품_2);

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(반려동물_식품_1.getKeyword());

        // then
        assertThat(조회된_식품).contains(반려동물_식품_1);
    }

    @Test
    void 동일한_키워드가_아닌_경우_조회하지_않는다() {
        // given
        petFoodRepository.save(키워드가_없는_반려동물_식품);
        petFoodRepository.save(다이어트_키워드_반려동물_식품);

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(다이어트_키워드_반려동물_식품.getKeyword());

        // then
        assertThat(조회된_식품).doesNotContain(키워드가_없는_반려동물_식품);
    }
}