package zipgo.controller.dto;

import zipgo.domain.PetFood;

public record PetFoodResponse(Long id, String imageUrl, String name, String purchaseUrl) {
    public static PetFoodResponse from(PetFood petFood) {
        return new PetFoodResponse(
                petFood.getId(),
                petFood.getImage(),
                petFood.getName(),
                petFood.getLink()
        );
    }
}
