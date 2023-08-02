package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;

public record FilterResponse(
        List<BrandResponse> brands,
        List<String> functionalities,
        List<String> mainIngredients,
        List<String> nutritionStandards
) {

    public static FilterResponse from(
            List<Brand> brands,
            List<String> primaryIngredients,
            List<String> functionalities
    ) {
        return new FilterResponse(
                brands.stream()
                        .map(BrandResponse::from)
                        .toList(),
                functionalities,
                primaryIngredients,
                List.of("유럽", "미국")
                );
    }

}
