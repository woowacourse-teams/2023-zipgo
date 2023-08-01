package zipgo.petfood.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodQueryServiceTest {

    @InjectMocks
    private PetFoodQueryService petFoodQueryService;

    @Mock
    private PetFoodQueryRepository petFoodQueryRepository;

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        Keyword 키워드 = new Keyword(1L, "diet");
        Brand 브랜드 = Brand.builder()
                .id(1L)
                .name("사료만드는 삼성")
                .nation("대한민국")
                .foundedYear(1999)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();
        PetFood 다이어트_키워드_식품 = 키워드_있는_식품_초기화(키워드, 브랜드);

        when(petFoodQueryRepository.findPetFoods("diet", null, null)).thenReturn(List.of(다이어트_키워드_식품));

        // when
        List<PetFood> 조회_결과 = petFoodQueryService.getPetFoodByDynamicValue("diet", null, null);

        // then
        assertAll(
                () -> assertThat(조회_결과).contains(다이어트_키워드_식품),
                () -> assertThat(조회_결과).hasSize(1)
        );
    }

    @Test
    void 존재하지_않는_키워드로_조회한다() {
        // given
        String 없는_키워드 = "없는 키워드";

        // when, then
        assertThat(petFoodQueryService.getPetFoodByDynamicValue(없는_키워드, null, null))
                .hasSize(0);
    }

}
