package zipgo.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PetFoodUpdateRequest (
        @JsonProperty("pet_food_name") String petFoodName,
        @JsonProperty("brand_name") String brandName,
        @JsonProperty("eu_standard") boolean euStandard,
        @JsonProperty("us_standard") boolean usStandard,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("functionalities") List<String> functionalities,
        @JsonProperty("primary_ingredients") List<String> primaryIngredients,
        @JsonProperty("has_research_center") boolean hasResearchCenter,
        @JsonProperty("has_resident_vet") boolean hasResidentVet
) {

    @Override
    public String toString() {
        return "PetFoodUpdateRequest{" +
                "petFoodName='" + petFoodName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", euStandard=" + euStandard +
                ", usStandard=" + usStandard +
                ", imageUrl='" + imageUrl + '\'' +
                ", functionalities=" + functionalities +
                ", primaryIngredients=" + primaryIngredients +
                ", hasResearchCenter=" + hasResearchCenter +
                ", hasResidentVet=" + hasResidentVet +
                '}';
    }

}

