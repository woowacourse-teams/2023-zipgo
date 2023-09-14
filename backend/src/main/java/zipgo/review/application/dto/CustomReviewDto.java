package zipgo.review.application.dto;

public record CustomReviewDto(
        Long petFoodId,
        int size,
        Long lastReviewId,
        Long sortById,
        Long petId,
        Long memberId
) {

}
