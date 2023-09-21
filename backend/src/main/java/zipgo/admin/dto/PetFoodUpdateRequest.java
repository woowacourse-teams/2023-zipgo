package zipgo.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PetFoodUpdateRequest (
        String petFoodName,
        String brandName,
        boolean euStandard,
        boolean usStandard,
        String imageUrl,
        List<String> functionalities,
        List<String> primaryIngredients
) {

}

