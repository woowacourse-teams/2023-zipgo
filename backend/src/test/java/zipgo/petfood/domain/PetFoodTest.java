package zipgo.petfood.domain;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.petfood.domain.type.PetFoodOption.PRIMARY_INGREDIENT;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetFoodTest {

    @Test
    void 아이디가_같으면_동등하다() {
        PetFood 식품_1 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        PetFood 식품_2 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        assertThat(식품_2).isEqualTo(식품_1);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        PetFood 식품_1 = PetFood.builder()
                .id(1L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        PetFood 식품_2 = PetFood.builder()
                .id(2L)
                .name("하이 너도 사료")
                .purchaseLink("purchase")
                .imageUrl("image")
                .build();

        assertThat(식품_2).isNotEqualTo(식품_1);
    }

    @Test
    void PetFood에서_같은_PetFoodEffect_객체는_생성되지_않는다() {
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
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> PetFood.builder()
                        .id(1L)
                        .name("하이 너도 사료")
                        .purchaseLink("purchase")
                        .imageUrl("image")
                        .petFoodEffects(Set.of(petFoodEffect, anotherPetFoodEffect))
                        .build()
        ).getMessage().contains("duplicate element");
    }

    @Test
    void 다른_PetFood에서_같은_PetFoodEffect_객체는_생성된다() {
        // given
        PetFoodEffect petFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("닭고기")
                .build();

        PetFoodEffect anotherPetFoodEffect = PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("소고기")
                .build();

        // when
        Assertions.assertDoesNotThrow(
                () -> PetFood.builder()
                        .id(1L)
                        .name("하이 너도 사료")
                        .purchaseLink("purchase")
                        .imageUrl("image")
                        .petFoodEffects(Set.of(petFoodEffect, anotherPetFoodEffect))
                        .build()
        );

        Assertions.assertDoesNotThrow(
                () -> PetFood.builder()
                        .id(2L)
                        .name("하이 너도 사료")
                        .purchaseLink("purchase")
                        .imageUrl("image")
                        .petFoodEffects(Set.of(petFoodEffect, anotherPetFoodEffect))
                        .build()
        );

    }

}
