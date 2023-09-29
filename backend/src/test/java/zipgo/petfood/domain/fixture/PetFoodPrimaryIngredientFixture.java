package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodPrimaryIngredient;
import zipgo.petfood.domain.PrimaryIngredient;

public class PetFoodPrimaryIngredientFixture {

    public static PetFoodPrimaryIngredient 식품_주원료_연관관계_매핑(PetFood petFood, PrimaryIngredient primaryIngredient) {
        PetFoodPrimaryIngredient petFoodPrimaryIngredient = new PetFoodPrimaryIngredient();
        petFoodPrimaryIngredient.changeRelations(petFood, primaryIngredient);
        return petFoodPrimaryIngredient;
    }

}
