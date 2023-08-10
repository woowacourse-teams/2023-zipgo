package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.petfood.domain.PetFood;

public record GetPetFoodsResponse(
        long totalCount,
        List<PetFoodResponse> petFoods
) {

    public static GetPetFoodsResponse from(long totalCount, List<PetFood> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(PetFoodResponse::from)
                .toList();

        return new GetPetFoodsResponse(totalCount, petFoodResponses);
    }

}
