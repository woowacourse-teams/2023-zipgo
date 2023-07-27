package zipgo.petfood.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.PetFood;

public record GetPetFoodResponse(
        Long id,
        String name,
        String imageUrl,
        String purchaseUrl,
        double rating,
        int reviewCount,
        List<String> primaryIngredients,
        NutrientStandardResponse hasStandard,
        List<String> functionality,
        PetFoodBrandResponse brand
) {

    public static GetPetFoodResponse of(PetFood petFood, double ratingAverage, int reviewCount) {
        return new GetPetFoodResponse(
                petFood.getId(),
                petFood.getName(),
                petFood.getImageUrl(),
                petFood.getPurchaseLink(),
                ratingAverage,
                reviewCount,
                petFood.getPrimaryIngredients(),
                NutrientStandardResponse.from(petFood.getHasStandard()),
                List.of(),
                PetFoodBrandResponse.from(petFood.getBrand())
        );
    }

    public record NutrientStandardResponse(
            @JsonProperty(value = "us")
            boolean hasUsStandard,

            @JsonProperty(value = "eu")
            boolean hasEuStandard
    ) {

        public static NutrientStandardResponse from(HasStandard hasStandard) {
            return new NutrientStandardResponse(hasStandard.getUnitedStates(), hasStandard.getEurope());
        }

    }

    public record PetFoodBrandResponse(
            String name,
            String state,
            int foundedYear,
            boolean hasResearchCenter,
            boolean hasResidentVet
    ) {

        public static PetFoodBrandResponse from(Brand brand) {
            return new PetFoodBrandResponse(
                    brand.getName(),
                    brand.getNation(),
                    brand.getFoundedYear(),
                    brand.isHasResearchCenter(),
                    brand.isHasResidentVet()
            );
        }

    }

}
