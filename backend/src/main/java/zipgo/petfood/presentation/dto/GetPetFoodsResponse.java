package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.petfood.domain.PetFood;

public record GetPetFoodsResponse(List<PetFoodResponse> petFoods) {

    public static GetPetFoodsResponse from(List<PetFood> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(PetFoodResponse::from)
                .toList();

        return new GetPetFoodsResponse(petFoodResponses);
    }

}
