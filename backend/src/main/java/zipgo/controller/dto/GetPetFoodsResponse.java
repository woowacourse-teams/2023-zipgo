package zipgo.controller.dto;

import zipgo.domain.PetFood;

import java.util.List;

public record GetPetFoodsResponse(List<PetFoodResponse> petFoods) {
    public static GetPetFoodsResponse from(List<PetFood> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(petFood -> PetFoodResponse.from(petFood))
                .toList();

        return new GetPetFoodsResponse(petFoodResponses);
    }
}
