package zipgo.petfood.presentation.dto;

import zipgo.petfood.domain.repository.dto.FilteredPetFoodResponse;

public record PetFoodResponse(
        Long id,
        String imageUrl,
        String brandName,
        String foodName,
        String purchaseUrl
) {

    public static PetFoodResponse from(FilteredPetFoodResponse petFood) {
        return new PetFoodResponse(
                petFood.id(),
                petFood.imageUrl(),
                petFood.brandName(),
                petFood.foodName(),
                petFood.purchaseUrl()
        );
    }

}
