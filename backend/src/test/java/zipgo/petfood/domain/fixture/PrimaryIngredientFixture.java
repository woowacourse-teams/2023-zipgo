package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;

import static zipgo.petfood.domain.PrimaryIngredient.builder;

public class PrimaryIngredientFixture {

    public static PrimaryIngredient 원재료_닭고기_식품(PetFood petFood) {
        PrimaryIngredient 닭고기_식품 = builder()
                .name("닭고기")
                .petFood(petFood)
                .build();
        닭고기_식품.changePetFood(petFood);
        return 닭고기_식품;
    }

    public static PrimaryIngredient 원재료_소고기_식품(PetFood petFood) {
        PrimaryIngredient 소고기_식품 = builder()
                .name("소고기")
                .petFood(petFood)
                .build();
        소고기_식품.changePetFood(petFood);
        return 소고기_식품;
    }

    public static PrimaryIngredient 원재료_돼지고기_식품(PetFood petFood) {
        PrimaryIngredient 돼지고기_식품 = builder()
                .name("돼지고기")
                .petFood(petFood)
                .build();
        돼지고기_식품.changePetFood(petFood);
        return 돼지고기_식품;
    }

    public static PrimaryIngredient 원재료_말미잘_식품(PetFood petFood) {
        PrimaryIngredient 말미잘_식품 = builder()
                .name("말미잘")
                .petFood(petFood)
                .build();
        말미잘_식품.changePetFood(petFood);
        return 말미잘_식품;
    }

}
