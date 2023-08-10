package zipgo.petfood.application.dto;

import java.util.List;

public record FilterDto(
        List<String> brands,
        List<String> nutritionStandards,
        List<String> mainIngredients,
        List<String> functionalities
) {

    public static FilterDto of(
            List<String> brands,
            List<String> nutritionStandards,
            List<String> mainIngredients,
            List<String> functionalities
    ) {
        return new FilterDto(brands, nutritionStandards, mainIngredients, functionalities);
    }

}
