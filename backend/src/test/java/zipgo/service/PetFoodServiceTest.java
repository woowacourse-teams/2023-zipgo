package zipgo.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zipgo.domain.PetFood;
import zipgo.domain.fixture.PetFoodFixture;
import zipgo.domain.repository.PetFoodRepository;

import static org.junit.jupiter.api.Assertions.*;
import static zipgo.domain.fixture.PetFoodFixture.*;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_1;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_2;

@SpringBootTest
class PetFoodServiceTest {
    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PetFoodService petFoodService;

    @Test
    void 전체_식품_목록을_조회한다() {
        // given
        petFoodRepository.save(반려동물_식품_1);
        petFoodRepository.save(반려동물_식품_2);

        // when
        List<PetFood> 조회_결과 = petFoodService.getAllPetFoods();

        // then
        Assertions.assertThat(조회_결과)
                .contains(반려동물_식품_1, 반려동물_식품_2);
    }
}