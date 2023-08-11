package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PrimaryIngredient;

public record FilterResponse(
        List<BrandResponse> brands,
        List<PrimaryIngredientResponse> mainIngredients,
        List<FunctionalityResponse> functionalities,
        List<NutrientStandardResponse> nutritionStandards
) {

    public static FilterResponse of(
            List<Brand> brands,
            List<PrimaryIngredient> primaryIngredients,
            List<Functionality> functionalities
    ) {
        return new FilterResponse(
                brands.stream()
                        .map(BrandResponse::from)
                        .toList(),
                primaryIngredients.stream()
                        .map(PrimaryIngredientResponse::from)
                        .toList(),
                functionalities.stream()
                        .map(FunctionalityResponse::from)
                        .toList(),
                List.of(NutrientStandardResponse.of(1L, "미국"),
                        NutrientStandardResponse.of(2L, "유럽"))
        );
    }

    public record BrandResponse(Long id, String brandUrl, String brandName) {

        public static BrandResponse from(Brand brand) {
            return new BrandResponse(brand.getId(), brand.getImageUrl(), brand.getName());
        }

    }

    public record PrimaryIngredientResponse(Long id, String ingredients) {

        public static PrimaryIngredientResponse from(PrimaryIngredient primaryIngredient) {
            return new PrimaryIngredientResponse(primaryIngredient.getId(), primaryIngredient.getName());
        }

    }

    public record FunctionalityResponse(Long id, String functionality) {

        public static FunctionalityResponse from(Functionality functionality) {
            return new FunctionalityResponse(functionality.getId(), functionality.getName());
        }

    }

    public record NutrientStandardResponse(Long id, String nation) {

        public static NutrientStandardResponse of(Long id, String nation) {
            return new NutrientStandardResponse(id, nation);
        }

    }

}
