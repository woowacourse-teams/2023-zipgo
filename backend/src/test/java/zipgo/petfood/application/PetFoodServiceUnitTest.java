package zipgo.petfood.application;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static zipgo.brand.domain.fixture.BrandFixture.*;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodServiceUnitTest {

    @InjectMocks
    private PetFoodQueryService petFoodQueryService;

    @Mock
    private PetFoodRepository petFoodRepository;

    @Mock
    private PetFoodQueryRepository petFoodQueryRepository;

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        Keyword 키워드 = new Keyword(1L, "diet");
        PetFood 다이어트_키워드_식품 = 키워드_있는_식품_초기화(키워드, mock());

        when(petFoodQueryRepository.searchPetFoodByDynamicValues("diet", null))
                .thenReturn(List.of(다이어트_키워드_식품));

        // when
        List<PetFood> 조회_결과 = petFoodQueryService.getPetFoodByDynamicValue("diet", null);

        // then
        assertAll(
                () -> assertThat(조회_결과).contains(다이어트_키워드_식품),
                () -> assertThat(조회_결과).hasSize(1)
        );
    }

    @Test
    void 식품_목록_아이디로_조회한다() {
        // given
        Long id = 1L;
        PetFood 식품 = 키워드_있는_식품_초기화(new Keyword(1L, "diet"), 식품_브랜드_생성하기());
        when(petFoodRepository.getById(id)).thenReturn(식품);

        // when
        PetFood petFood = petFoodQueryService.getPetFoodBy(id);

        // then
        assertAll(
                () -> assertThat(petFood.getKeyword().getId()).isEqualTo(1L),
                () -> assertThat(petFood.getKeyword().getName()).isEqualTo("diet")
        );
    }

}
