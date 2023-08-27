package zipgo.petfood.presentation.dto;

import java.util.List;

public record FilterRequest(
        List<String> brands,
        List<String> nutritionStandards,
        List<String> functionalities,
        List<String> mainIngredients
) {

    public static FilterRequest of(
            List<String> brands,
            List<String> nutritionStandards,
            List<String> functionalities,
            List<String> mainIngredients
    ) {
        return new FilterRequest(brands, nutritionStandards, functionalities, mainIngredients);
    }

}
