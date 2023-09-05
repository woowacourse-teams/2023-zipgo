package zipgo.petfood.presentation.dto;

public record PrimaryIngredientSelectResponse(
        Long id,
        String name
) {

    public static PrimaryIngredientSelectResponse of(Long id, String name) {
        return new PrimaryIngredientSelectResponse(id, name);
    }

}
