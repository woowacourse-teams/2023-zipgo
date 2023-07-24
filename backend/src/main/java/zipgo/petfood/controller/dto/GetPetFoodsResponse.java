package zipgo.petfood.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import zipgo.petfood.domain.PetFood;

public record GetPetFoodsResponse(@JsonProperty(value = "foodList") List<PetFoodResponse> petFoods) {

    public static GetPetFoodsResponse from(List<PetFood> petFoods) {
        List<PetFoodResponse> petFoodResponses = petFoods.stream()
                .map(petFood -> PetFoodResponse.from(petFood))
                .toList();

        return new GetPetFoodsResponse(petFoodResponses);
    }

}
