package zipgo.review.application.dto;

import zipgo.pet.exception.PetFoodIdNotNullException;
import zipgo.pet.exception.ReviewSizeNegativeException;

public record GetReviewQueryRequest(
        Long petFoodId,
        int size,
        Long lastReviewId
) {

    public GetReviewQueryRequest {
        if (petFoodId == null) {
            throw new PetFoodIdNotNullException();
        }
        if (size <= 0) {
            throw new ReviewSizeNegativeException();
        }
    }

}
