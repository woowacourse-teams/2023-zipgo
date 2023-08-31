package zipgo.petfood.domain;

import org.junit.jupiter.api.Test;
import zipgo.petfood.domain.type.PetFoodOption;

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

    @Test
    void PetFoodEffect_객체의_값이_같으면_같은_객체다() {
        // given
        PetFoodEffect petFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("닭고기")
                .build();

        PetFoodEffect anotherPetFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("닭고기")
                .build();

        // when
        boolean equals = petFoodEffect.equals(anotherPetFoodEffect);

        // then
        assertThat(equals).isTrue();
    }

    @Test
    void PetFoodEffect_객체의_값이_다르면_다른_객체다() {
        // given
        PetFoodEffect petFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("소고기")
                .build();

        PetFoodEffect anotherPetFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("돼지고기")
                .build();

        // when
        boolean equals = petFoodEffect.equals(anotherPetFoodEffect);

        // then
        assertThat(equals).isFalse();
    }



}
