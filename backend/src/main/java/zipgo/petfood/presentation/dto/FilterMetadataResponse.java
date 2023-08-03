package zipgo.petfood.presentation.dto;

import java.util.List;

public record FilterMetadataResponse(
        List<String> keywords,
        FilterResponse filters
) {

    public static FilterMetadataResponse of(FilterResponse filterResponse) {
        return new FilterMetadataResponse(
                List.of("nutritionStandards", "mainIngredients", "brands", "functionalities"),
                filterResponse
        );
    }

}
