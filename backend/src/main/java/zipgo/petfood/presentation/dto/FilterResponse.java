package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;

public record FilterResponse(
        List<BrandResponse> brands,
        List<FunctionalityResponse> functionalities,
        List<PrimaryIngredientResponse> mainIngredients,
        List<NutrientStandardResponse> nutritionStandards
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
                functionalities.stream().map(functionality -> new FunctionalityResponse(0L, functionality)).toList(),
                primaryIngredients.stream().map(ingredient -> new PrimaryIngredientResponse(0L, ingredient)).toList(),
                List.of(new NutrientStandardResponse(0L, "미국"), new NutrientStandardResponse(0L, "유럽"))
        );
    }

    public record BrandResponse(
            Long id,
            String brandUrl,
            String brandName
    ) {

        public static BrandResponse from(Brand brand) {
            return new BrandResponse(brand.getId(), brand.getImageUrl(), brand.getName());
        }

    }

    public record PrimaryIngredientResponse(Long id, String ingredients) {

    }

    public record FunctionalityResponse(Long id, String functionality) {

    }

    public record NutrientStandardResponse(Long id, String nation) {

    }

}
