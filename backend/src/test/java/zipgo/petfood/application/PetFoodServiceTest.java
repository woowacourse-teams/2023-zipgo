package zipgo.petfood.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.KeywordRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;

@Transactional
@SpringBootTest
class PetFoodServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodQueryService petFoodQueryService;

    private Brand 브랜드_조회하기() {
        return brandRepository.findAll().get(0);
    }

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드_조회하기()));
        PetFood 다이어트_키워드_식품 = 다이어트_키워드_식품_저장();

        // when
        List<PetFood> 조회_결과 = petFoodQueryService.getPetFoodByDynamicValue("diet", null, null);

        // then
        assertAll(() -> {
            assertThat(조회_결과).contains(다이어트_키워드_식품);
            assertThat(조회_결과).doesNotContain(키워드가_없는_식품);
        });
    }

    private PetFood 다이어트_키워드_식품_저장() {
        Keyword 다이어트_키워드 = keywordRepository.findByName("diet").orElseThrow();
        PetFood 다이어트_키워드_식품 = petFoodRepository.save(키워드_있는_식품_초기화(다이어트_키워드, 브랜드_조회하기()));
        return 다이어트_키워드_식품;
    }

    @Test
    void 존재하지_않는_키워드로_조회한다() {
        // given
        String 없는_키워드 = "없는 키워드";

        // when
        List<PetFood> responses = petFoodQueryService.getPetFoodByDynamicValue(없는_키워드, null, null);

        // then
        assertThat(responses).hasSize(0);
    }

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        // given
        Brand 브랜드 = brandRepository.findAll().get(0);
        PetFood 테스트용_식품 = 키워드_없이_식품_초기화(브랜드);
        Long 아이디 = petFoodRepository.save(테스트용_식품).getId();

        // when
        PetFood 조회된_식품 = petFoodQueryService.getPetFoodBy(아이디);

        // then
        assertThat(조회된_식품).isEqualTo(테스트용_식품);
    }

}
