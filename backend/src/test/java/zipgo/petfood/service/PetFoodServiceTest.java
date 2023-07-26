package zipgo.petfood.service;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있이_식품_초기화;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.petfood.application.PetFoodService;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.KeywordRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.exception.KeywordException;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodServiceTest {

    @InjectMocks
    private PetFoodService petFoodService;

    @Mock
    private PetFoodRepository petFoodRepository;

    @Mock
    private KeywordRepository keywordRepository;


    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        Keyword 키워드 = new Keyword(1L, "diet");
        PetFood 다이어트_키워드_식품 = 키워드_있이_식품_초기화(키워드);

        when(keywordRepository.findByName("diet")).thenReturn(of(키워드));
        when(petFoodRepository.findByKeyword(키워드)).thenReturn(List.of(다이어트_키워드_식품));

        // when
        List<PetFood> 조회_결과 = petFoodService.getPetFoodHaving("diet");

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
        assertThatThrownBy(() -> petFoodService.getPetFoodHaving(없는_키워드))
                .isInstanceOf(KeywordException.NotFound.class);
    }

}
