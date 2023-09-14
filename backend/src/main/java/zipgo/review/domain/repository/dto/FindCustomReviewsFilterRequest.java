package zipgo.review.domain.repository.dto;

import lombok.Builder;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.Pet;
import zipgo.review.application.SortBy;
import zipgo.review.application.dto.CustomReviewDto;
import zipgo.review.exception.NoPetFoodIdException;
import zipgo.review.exception.NonPositiveSizeException;

public record FindCustomReviewsFilterRequest(
        Long petFoodId,
        int size,
        Long lastReviewId,
        SortBy sortBy,
        AgeGroup ageGroup,
        Long breedId,
        Long memberId
) {

    @Builder
    public FindCustomReviewsFilterRequest {
        if (petFoodId == null) {
            throw new NoPetFoodIdException();
        }
        if (size <= 0) {
            throw new NonPositiveSizeException();
        }
        if (sortBy == null) {
            sortBy = SortBy.RECENT;
        }
    }

    public static FindCustomReviewsFilterRequest of(
            CustomReviewDto customReviewDto,
            Pet pet
    ) {
        return new FindCustomReviewsFilterRequest(
                customReviewDto.petFoodId(),
                customReviewDto.size(),
                customReviewDto.lastReviewId(),
                SortBy.from(customReviewDto.sortById()),
                AgeGroup.from(pet.calculateCurrentAge()),
                pet.getBreeds().getId(),
                customReviewDto.memberId()
        );
    }

}
