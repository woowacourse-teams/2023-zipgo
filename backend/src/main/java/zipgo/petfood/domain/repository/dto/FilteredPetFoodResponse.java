package zipgo.petfood.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public record FilteredPetFoodResponse(
        Long id,
        String imageUrl,
        String brandName,
        String foodName,
        String purchaseUrl
) {

    public static FilteredPetFoodResponse of(
            Long id,
            String imageUrl,
            String brandName,
            String foodName,
            String purchaseUrl
    ) {
        return new FilteredPetFoodResponse(id, imageUrl, brandName, foodName, purchaseUrl);
    }

    @QueryProjection
    public FilteredPetFoodResponse {
    }

}
