package zipgo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.domain.fixture.PetFoodFixture.다이어트_키워드_반려동물_식품;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_1;
import static zipgo.domain.fixture.PetFoodFixture.반려동물_식품_2;
import static zipgo.domain.fixture.PetFoodFixture.키워드가_없는_반려동물_식품;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zipgo.domain.PetFood;
import zipgo.domain.repository.PetFoodRepository;
import zipgo.exception.KeywordException;

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
        assertThat(조회_결과).contains(반려동물_식품_1, 반려동물_식품_2);
    }

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        petFoodRepository.save(키워드가_없는_반려동물_식품);
        petFoodRepository.save(다이어트_키워드_반려동물_식품);
        String 다이어트 = "diet";

        // when
        List<PetFood> 조회_결과 = petFoodService.getPetFoodHaving(다이어트);

        // then
        assertAll(() -> {
            assertThat(조회_결과).contains(다이어트_키워드_반려동물_식품);
            assertThat(조회_결과).doesNotContain(키워드가_없는_반려동물_식품);
        });
    }

    @Test
    void 존재하지_않는_키워드로_조회한다() {
        // given
        petFoodRepository.save(키워드가_없는_반려동물_식품);
        petFoodRepository.save(다이어트_키워드_반려동물_식품);
        String 없는_키워드 = "없는 키워드";

        // when
        // then
        assertThatThrownBy(() -> petFoodService.getPetFoodHaving(없는_키워드))
                .isInstanceOf(KeywordException.NotFound.class);
    }
}