package zipgo.petfood.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.type.PetFoodOption.FUNCTIONALITY;
import static zipgo.petfood.domain.type.PetFoodOption.PRIMARY_INGREDIENT;

class PetFoodEffectTest {

    @Test
    void 사료의_효과가_일치하는지_판단한다() {
        // given
        PetFoodEffect functionalityPetFood = PetFoodEffect.builder()
                .petFoodOption(FUNCTIONALITY)
                .description("튼튼")
                .build();

        PetFoodEffect primaryIngredientPetFood = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("닭고기")
                .build();

        // when, then
        assertAll(
                () -> assertThat(functionalityPetFood.isEqualTo(FUNCTIONALITY))
                        .isTrue(),
                () -> assertThat(primaryIngredientPetFood.isEqualTo(PRIMARY_INGREDIENT))
                        .isTrue()
        );
    }
}
