package zipgo.petfood.dto.response;

public record PetFoodResponse(
        Long id,
        String imageUrl,
        String brandName,
        String foodName
) {

    public static PetFoodResponse from(GetPetFoodQueryResponse petFood) {
        return new PetFoodResponse(
                petFood.petFoodId(),
                petFood.imageUrl(),
                petFood.brandName(),
                petFood.foodName()
        );
    }

}
