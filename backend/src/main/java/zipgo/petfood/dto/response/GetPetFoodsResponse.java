package zipgo.petfood.dto.response;

import java.util.List;

public record GetPetFoodsResponse(
        long totalCount,
        List<PetFoodResponse> petFoods
) {

    public static GetPetFoodsResponse from(long totalCount, List<GetPetFoodQueryResponse> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(PetFoodResponse::from)
                .toList();

        return new GetPetFoodsResponse(totalCount, petFoodResponses);
    }

}
