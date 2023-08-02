package zipgo.petfood.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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

}
