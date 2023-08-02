package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.PetFood;

public record FilterResponse(
        List<String> nutritionStandards,
        List<String> mainIngredients,
        List<BrandResponse> brands,
        List<String> functionality
) {

    public static FilterResponse from(PetFood petFood, List<Brand> brands) {
        return new FilterResponse(
                List.of("유럽", "미국"),
                petFood.getPrimaryIngredients(),
                brands.stream()
                        .map(BrandResponse::from)
                        .toList(),
                petFood.getFunctionality()
        );
    }

}
