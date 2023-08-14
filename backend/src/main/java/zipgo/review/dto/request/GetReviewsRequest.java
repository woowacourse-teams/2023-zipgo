package zipgo.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;
import zipgo.review.domain.repository.dto.FindReviewsQueryRequest;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

public record GetReviewsRequest(

        @NotNull
        Long petFoodId,
        @Positive
        Integer size,
        Long lastReviewId,
        Long sortById,
        List<Long> petSizeId,
        List<Long> ageGroupId
) {

    public FindReviewsQueryRequest toQueryRequest() {
        return new FindReviewsQueryRequest(
                petFoodId,
                size(),
                lastReviewId,
                SortBy.from(sortById()),
                petSizeId(),
                ageGroupId().stream().map(AgeGroup::from).toList());
    }

    @Override
    public Long sortById() {
        return requireNonNullElse(sortById, 1L);
    }

    @Override
    public Integer size() {
        return requireNonNullElse(size, 10);
    }

    public List<Long> petSizeId() {
        return requireNonNullElse(petSizeId, emptyList());
    }

    @Override
    public List<Long> ageGroupId() {
        return requireNonNullElse(ageGroupId, emptyList());
    }

}