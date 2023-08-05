package zipgo.petfood.application;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;

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

//    @Test
//    @Disabled("230802 메타데이터 API 요구사항 변경사항 반영 전 비활성화")
//    void 필터링에_필요한_메타데이터를_조회한다() {
//        // given
//        List<Brand> 식품_브랜드_리스트 = List.of(식품_브랜드_생성하기());
//        when(brandRepository.findAll()).thenReturn(식품_브랜드_리스트);
//        when(petFoodRepository.findAllPrimaryIngredients()).thenReturn(List.of("닭고기,쌀", "닭고기", "말미잘"));
//        when(petFoodRepository.findAllFunctionalities()).thenReturn(List.of("튼튼,짱"));
//
//        // when
//        FilterResponse metadataForFilter = petFoodQueryService.getMetadataForFilter();

        // then
//        assertAll(
//                () -> assertThat(metadataForFilter.brands().get(0).brandName()).isEqualTo(식품_브랜드_리스트.get(0).getName()),
//                () -> assertThat(metadataForFilter.mainIngredients()).contains("닭고기", "쌀", "말미잘"),
//                () -> assertThat(metadataForFilter.functionalities()).contains("튼튼", "짱")
//        );
//    }

}
