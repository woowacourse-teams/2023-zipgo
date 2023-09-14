package zipgo.review.application.dto;

import zipgo.pet.exception.PetFoodIdNotNullException;
import zipgo.pet.exception.ReviewSizeNegativeException;

public record CustomReviewDto(
        Long petFoodId,
        int size,
        Long lastReviewId,
        Long sortById,
        Long petId,
        Long memberId
) {

    public CustomReviewDto {
        if (petFoodId == null) {
            throw new PetFoodIdNotNullException();
        }
        if (size <= 0) {
            throw new ReviewSizeNegativeException();
        }
        if (sortById == null) {
            sortById = 1L;
        }
    }

}
