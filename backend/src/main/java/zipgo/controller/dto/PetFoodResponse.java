package zipgo.controller.dto;

import zipgo.domain.PetFood;

public record PetFoodResponse(Long id, String imageUrl, String name, String purchaseUrl) {
    public static PetFoodResponse from(PetFood petFood) {
        return new PetFoodResponse(
                petFood.getId(),
                petFood.getImageUrl(),
                petFood.getName(),
                petFood.getPurchaseLink()
        );
    }
}
