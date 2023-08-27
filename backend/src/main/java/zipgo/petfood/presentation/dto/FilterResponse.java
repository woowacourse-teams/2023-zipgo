package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.PetFoodEffect;

public record FilterResponse(
        List<BrandResponse> brands,
        List<FunctionalityResponse> functionalities,
        List<PrimaryIngredientResponse> mainIngredients,
        List<NutrientStandardResponse> nutritionStandards
) {

    public static FilterResponse of(
            List<Brand> brands,
            List<PetFoodEffect> functionalities,
            List<PetFoodEffect> primaryIngredients
    ) {
        return new FilterResponse(
                brands.stream()
                        .map(BrandResponse::from)
                        .toList(),
                functionalities.stream()
                        .map(FunctionalityResponse::from)
                        .toList(),
                primaryIngredients.stream()
                        .map(PrimaryIngredientResponse::from)
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

    public record FunctionalityResponse(Long id, String functionality) {

        public static FunctionalityResponse from(PetFoodEffect petFoodEffect) {
            return new FunctionalityResponse(petFoodEffect.getId(), petFoodEffect.getDescription());
        }

    }

    public record PrimaryIngredientResponse(Long id, String primaryIngredient) {

        public static PrimaryIngredientResponse from(PetFoodEffect petFoodEffect) {
            return new PrimaryIngredientResponse(petFoodEffect.getId(), petFoodEffect.getDescription());
        }

    }

    public record NutrientStandardResponse(Long id, String nation) {

        public static NutrientStandardResponse of(Long id, String nation) {
            return new NutrientStandardResponse(id, nation);
        }

    }

}
