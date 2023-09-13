package zipgo.petfood.dto.request;

public record PetFoodSelectRequest(
        String keyword,
        String brand,
        String primaryIngredients
) {

}
