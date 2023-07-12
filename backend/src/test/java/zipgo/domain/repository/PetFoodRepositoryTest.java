package zipgo.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import zipgo.domain.PetFood;
import zipgo.domain.repository.fake.PetFoodFakeRepository;

import java.util.List;

import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_1;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_2;

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
        Assertions.assertThat(petFoods).contains(반려동물_식품_1, 반려동물_식품_2);
    }
}