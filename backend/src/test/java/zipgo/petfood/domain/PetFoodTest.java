package zipgo.petfood.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zipgo.petfood.domain.PetFood;

class PetFoodTest {

    @Test
    void 아이디가_같으면_동등하다() {
        PetFood 식품_1 = new PetFood(2L, "하이 난 사료", "purchase", "image", null);
        PetFood 식품_2 = new PetFood(2L, "하이 너도 사료", "purchase", "image2", null);

        assertThat(식품_2).isEqualTo(식품_1);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        PetFood 식품_1 = new PetFood(2L, "하이 난 사료", "purchase", "image", null);
        PetFood 식품_2 = new PetFood(3L, "하이 난 사료", "purchase", "image", null);

        assertThat(식품_2).isNotEqualTo(식품_1);
    }

}
