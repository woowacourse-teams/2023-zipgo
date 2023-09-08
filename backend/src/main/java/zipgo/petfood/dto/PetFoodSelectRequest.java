package zipgo.petfood.dto;

public record PetFoodSelectRequest(
        String keyword,
        String brand,
        String primaryIngredients
) {

}
