package zipgo.controller.dto;

import java.util.List;
import zipgo.domain.PetFood;

public record GetPetFoodsResponse(List<PetFoodResponse> petFoods) {
    public static GetPetFoodsResponse from(List<PetFood> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(petFood -> PetFoodResponse.from(petFood))
                .toList();

        return new GetPetFoodsResponse(petFoodResponses);
    }
}
