package zipgo.petfood.presentation.dto;

import zipgo.petfood.domain.PetFood;

public record PetFoodResponse(
        Long id,
        String imageUrl,
        String brandName,
        String foodName,
        String purchaseUrl
) {

    public static PetFoodResponse from(PetFood petFood) {
        return new PetFoodResponse(
                petFood.getId(),
                petFood.getImageUrl(),
                petFood.getBrand().getName(),
                petFood.getName(),
                petFood.getPurchaseLink()
        );
    }

}
