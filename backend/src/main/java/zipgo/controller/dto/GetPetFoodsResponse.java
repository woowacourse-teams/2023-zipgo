package zipgo.controller.dto;

import java.util.List;

public class GetPetFoodsResponse {
    private final List<PetFoodResponse> petFoods;

    public GetPetFoodsResponse(List<PetFoodResponse> petFoods) {
        this.petFoods = petFoods;
    }

    public List<PetFoodResponse> getPetFoods() {
        return petFoods;
    }
}