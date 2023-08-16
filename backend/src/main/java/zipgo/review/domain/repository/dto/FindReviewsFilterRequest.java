package zipgo.review.domain.repository.dto;

import java.util.List;
import lombok.Builder;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;

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
            throw new IllegalArgumentException("petFoodId는 null 이 될 수 없습니다.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size 는 양수 이어야 합니다.");
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


}
