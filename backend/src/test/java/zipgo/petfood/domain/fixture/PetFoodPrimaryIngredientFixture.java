package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodPrimaryIngredient;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

public class PetFoodPrimaryIngredientFixture {

    public static PetFoodPrimaryIngredient 식품_주원료_연관관계_매핑(PetFood petFood, PrimaryIngredient primaryIngredient) {
        PetFoodPrimaryIngredient petFoodPrimaryIngredient = new PetFoodPrimaryIngredient();
        petFoodPrimaryIngredient.changeRelations(petFood, primaryIngredient);
        return petFoodPrimaryIngredient;
    }

    public static PetFoodPrimaryIngredient 식품_주원료_추가(PetFood petFood,
                                               PrimaryIngredient primaryIngredient,
                                               PrimaryIngredientRepository primaryIngredientRepository
    ) {
        return 식품_주원료_연관관계_매핑(petFood, primaryIngredientRepository.save(primaryIngredient));
    }

}
