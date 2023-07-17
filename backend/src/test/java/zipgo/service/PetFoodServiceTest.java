package zipgo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.repository.KeywordRepository;
import zipgo.domain.repository.PetFoodRepository;
import zipgo.exception.KeywordException;

@SpringBootTest
class PetFoodServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private PetFoodService petFoodService;

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(키워드_없이_식품_초기화());
        PetFood 다이어트_키워드_식품 = 다이어트_키워드_식품_저장();

        // when
        List<PetFood> 조회_결과 = petFoodService.getPetFoodHaving("diet");

        // then
        assertAll(() -> {
            assertThat(조회_결과).contains(다이어트_키워드_식품);
            assertThat(조회_결과).doesNotContain(키워드가_없는_식품);
        });
    }

    private PetFood 다이어트_키워드_식품_저장() {
        Keyword 다이어트_키워드 = keywordRepository.findByName("diet").orElseThrow();
        PetFood 다이어트_키워드_식품 = petFoodRepository.save(
                new PetFood("[고집] 돌아온 배배",
                        "https://github.com/woowacourse-teams/2023-zipgo",
                        "https://avatars.githubusercontent.com/u/94087228?v=4", 다이어트_키워드));
        return 다이어트_키워드_식품;
    }

    @Test
    void 존재하지_않는_키워드로_조회한다() {
        // given
        String 없는_키워드 = "없는 키워드";

        // when
        // then
        assertThatThrownBy(() -> petFoodService.getPetFoodHaving(없는_키워드))
                .isInstanceOf(KeywordException.NotFound.class);
    }

}
