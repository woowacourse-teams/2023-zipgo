package zipgo.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

public record GetReviewsRequest(

        @NotNull
        Long petFoodId,
        @Positive
        Integer size,
        Long lastReviewId,
        Long sortById,
        List<Long> breedSizeId,
        List<Long> ageGroupId
) {

    @Override
    public Long sortById() {
        return requireNonNullElse(sortById, 1L);
    }

    @Override
    public Integer size() {
        return requireNonNullElse(size, 10);
    }

    @Override
    public List<Long> breedSizeId() {
        return requireNonNullElse(breedSizeId, List.of(1L, 2L, 3L));
    }

    @Override
    public List<Long> ageGroupId() {
        return requireNonNullElse(ageGroupId, List.of(1L, 2L, 3L));
    }

}
