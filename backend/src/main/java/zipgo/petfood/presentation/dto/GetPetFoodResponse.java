package zipgo.petfood.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GetPetFoodResponse(
        Long id,
        String name,
        String imageUrl,
        String purchaseUrl,
        double rating,
        int reviewCount,
        ProteinSourceResponse proteinSource,
        NutrientStandardResponse hasStandard,
        List<String> functionality,
        PetFoodBrandResponse brand
) {

    public record NutrientStandardResponse(
            @JsonProperty(value = "us")
            boolean hasUsStandard,

            @JsonProperty(value = "eu")
            boolean hasEuStandard
    ) {

    }

    public record PetFoodBrandResponse(
            String name,
            String state,
            int foundedYear,
            boolean hasResearchCenter,
            boolean hasResidentVet
    ) {

    }

    public record ProteinSourceResponse(
            List<String> primary,
            List<String> secondary
    ) {

    }

}
