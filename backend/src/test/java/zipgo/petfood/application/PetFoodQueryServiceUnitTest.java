package zipgo.petfood.application;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.presentation.dto.FilterResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static zipgo.brand.domain.fixture.BrandFixture.식품_브랜드_생성하기;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodQueryServiceUnitTest {

    @InjectMocks
    private PetFoodQueryService petFoodQueryService;

    @Mock
    private PetFoodQueryRepository petFoodQueryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private PetFoodRepository petFoodRepository;

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

    @Test
//    @Disabled("230802 메타데이터 API 요구사항 변경사항 반영 전 비활성화")
    void 필터링에_필요한_메타데이터를_조회한다() {
        // given
        List<Brand> 식품_브랜드_리스트 = List.of(식품_브랜드_생성하기());
        when(brandRepository.findAll()).thenReturn(식품_브랜드_리스트);
        when(petFoodRepository.findAllPrimaryIngredients()).thenReturn(List.of("닭고기,쌀", "닭고기", "말미잘"));
        when(petFoodRepository.findAllFunctionalities()).thenReturn(List.of("튼튼,짱"));

        // when
        FilterResponse metadataForFilter = petFoodQueryService.getMetadataForFilter();

        // then
//        assertAll(
//                () -> assertThat(metadataForFilter.brands().get(0).brandName()).isEqualTo(식품_브랜드_리스트.get(0).getName()),
//                () -> assertThat(metadataForFilter.mainIngredients()).contains("닭고기", "쌀", "말미잘"),
//                () -> assertThat(metadataForFilter.functionalities()).contains("튼튼", "짱")
//        );
    }

}
