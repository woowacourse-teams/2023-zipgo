package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodFunctionality;

public class PetFoodFunctionalityFixture {

    public static PetFoodFunctionality 식품_기능성_연관관계_매핑(PetFood petFood, Functionality functionality) {
        PetFoodFunctionality petFoodFunctionality = PetFoodFunctionality.builder()
                .petFood(petFood)
                .functionality(functionality)
                .build();
        petFoodFunctionality.changeRelations(petFood, functionality);
        return petFoodFunctionality;
    }

}
