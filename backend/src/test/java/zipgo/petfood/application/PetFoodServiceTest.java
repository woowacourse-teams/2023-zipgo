package zipgo.petfood.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.exception.PetFoodException;

@Transactional
@SpringBootTest
class PetFoodServiceTest {

    @InjectMocks
    private PetFoodService petFoodService;

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        // given
        Long 아이디 = 1L;

        // when
        PetFood 조회된_식품 = petFoodService.getPetFoodBy(아이디);

        // then
        PetFood expected = PetFood.builder().id(아이디).build();
        Assertions.assertThat(조회된_식품).isEqualTo(expected);
    }

    @Test
    void 존재하지_않는_아이디로_조회시_예외가_발생한다() {
        // given
        Long 존재하지_않는_아이디 = -1L;

        // when, then
        assertThatThrownBy(() -> petFoodService.getPetFoodBy(존재하지_않는_아이디))
                .isInstanceOf(PetFoodException.NotFound.class);
    }

}
