package zipgo.review.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

public record GetReviewsRequest(
        @NotNull(message = "식품 id를 입력해주세요.")
        Long petFoodId,
        @Positive(message = "size는 0보다 커야합니다.")
        Integer size,
        Long lastReviewId,
        Long sortById,
        List<Long> petSizeId,
        List<Long> ageGroupId,
        List<Long> breedId
) {

    public FindReviewsFilterRequest toQueryRequest(Long memberId) {
        return new FindReviewsFilterRequest(
                petFoodId,
                size(),
                lastReviewId,
                SortBy.from(sortById()),
                petSizeId(),
                ageGroupId().stream().map(AgeGroup::from).toList(),
                breedId(),
                memberId
        );
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

    @Override
    public List<Long> breedId() {
        return requireNonNullElse(breedId, emptyList());
    }

}
