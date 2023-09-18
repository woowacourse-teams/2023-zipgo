package zipgo.review.domain.repository.dto;

import java.util.List;
import lombok.Builder;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;
import zipgo.review.exception.NoPetFoodIdException;
import zipgo.review.exception.NonPositiveSizeException;

import static java.util.Collections.emptyList;


public record FindReviewsFilterRequest(
        Long petFoodId,
        int size,
        Long lastReviewId,
        SortBy sortBy,
        List<Long> petSizes,
        List<AgeGroup> ageGroups,
        List<Long> breedIds,
        Long memberId
) {

    @Builder
    public FindReviewsFilterRequest {
        if (petFoodId == null) {
            throw new NoPetFoodIdException();
        }
        if (size <= 0) {
            throw new NonPositiveSizeException();
        }
        if (sortBy == null) {
            sortBy = SortBy.RECENT;
        }
        if (ageGroups == null) {
            ageGroups = emptyList();
        }
        if (petSizes == null) {
            petSizes = emptyList();
        }
        if (breedIds == null) {
            breedIds = emptyList();
        }
    }

    public FindReviewsFilterRequest toMixedBreedRequest(List<Long> mixBreedIds) {
        return new FindReviewsFilterRequest(
                petFoodId,
                size,
                lastReviewId,
                sortBy,
                petSizes,
                ageGroups,
                mixBreedIds,
                memberId
        );
    }

}
