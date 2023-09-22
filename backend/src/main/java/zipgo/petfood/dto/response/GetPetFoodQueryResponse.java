package zipgo.petfood.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record GetPetFoodQueryResponse(
        long petFoodId,
        String foodName,
        String brandName,
        String imageUrl
) {

    @QueryProjection
    public GetPetFoodQueryResponse {

    }

}
