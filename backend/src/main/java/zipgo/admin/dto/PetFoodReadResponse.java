package zipgo.admin.dto;

import java.util.List;
import zipgo.petfood.domain.PetFood;

public record PetFoodReadResponse(
        String brandName,
        String foodName,
        String imageUrl,
        boolean euStandard,
        boolean usStandard,
        List<String> functionalities,
        List<String> primaryIngredients,
        boolean hasResearchCenter,
        boolean hasResidentVet
) {

    public static PetFoodReadResponse from(PetFood petFood) {
        return new PetFoodReadResponse(
                petFood.getBrand().getName(),
                petFood.getName(),
                petFood.getImageUrl(),
                petFood.getHasStandard().getEurope(),
                petFood.getHasStandard().getUnitedStates(),
                petFood.getPetFoodFunctionalities().stream()
                        .map(petFoodFunctionality -> petFoodFunctionality.getFunctionality().getName())
                        .toList(),
                petFood.getPetFoodPrimaryIngredients().stream()
                        .map(petFoodPrimaryIngredient -> petFoodPrimaryIngredient.getPrimaryIngredient().getName())
                        .toList(),
                petFood.getBrand().isHasResearchCenter(),
                petFood.getBrand().isHasResidentVet()
        );
    }

}
