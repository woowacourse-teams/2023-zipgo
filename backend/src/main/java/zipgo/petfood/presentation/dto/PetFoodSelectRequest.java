package zipgo.petfood.presentation.dto;

public record PetFoodSelectRequest(
        String keyword,
        String brand,
        String primaryIngredients
) {

}
