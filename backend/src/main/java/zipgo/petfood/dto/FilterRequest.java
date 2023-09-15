package zipgo.petfood.dto;

import java.util.List;

public record FilterRequest(
        List<String> brands,
        List<String> nutritionStandards,
        List<String> mainIngredients,
        List<String> functionalities
) {

    public static FilterRequest of(
            List<String> brands,
            List<String> nutritionStandards,
            List<String> mainIngredients,
            List<String> functionalities
    ) {
        return new FilterRequest(brands, nutritionStandards, mainIngredients, functionalities);
    }

}
