package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodEffect;

import static zipgo.petfood.domain.type.PetFoodOption.FUNCTIONALITY;
import static zipgo.petfood.domain.type.PetFoodOption.PRIMARY_INGREDIENT;

public class PetFoodEffectFixture {

    public static void 식품_효과_연관관계_매핑(PetFood petFood, PetFoodEffect petFoodEffect) {
        petFoodEffect.changeRelation(petFood);
    }

    public static PetFoodEffect 기능성_튼튼() {
        return PetFoodEffect.builder()
                .petFoodOption(FUNCTIONALITY)
                .description("튼튼")
                .build();
    }

    public static PetFoodEffect 기능성_짱짱() {
        return PetFoodEffect.builder()
                .petFoodOption(FUNCTIONALITY)
                .description("짱짱")
                .build();
    }

    public static PetFoodEffect 기능성_다이어트() {
        return PetFoodEffect.builder()
                .petFoodOption(FUNCTIONALITY)
                .description("다이어트")
                .build();
    }

    public static PetFoodEffect 원재료_소고기() {
        return PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("소고기")
                .build();
    }

    public static PetFoodEffect 원재료_돼지고기() {
        return PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("돼지고기")
                .build();
    }

    public static PetFoodEffect 원재료_닭고기() {
        return PetFoodEffect.builder()
                .petFoodOption(PRIMARY_INGREDIENT)
                .description("닭고기")
                .build();
    }

}
