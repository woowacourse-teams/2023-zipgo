package zipgo.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import zipgo.review.application.dto.CustomReviewDto;

public record GetCustomReviewRequest(
        @NotNull(message = "식품 id를 입력해주세요.")
        Long petFoodId,
        @Positive(message = "size는 0보다 커야합니다.")
        Integer size,
        Long lastReviewId,
        Long sortById,
        @NotNull(message = "반려동물 id를 입력해주세요.")
        Long petId
) {

    public GetCustomReviewRequest {
        if (sortById == null) {
            sortById = 1L;
        }
    }

    public CustomReviewDto toDto(Long memberId) {
        return new CustomReviewDto(
                this.petFoodId,
                this.size,
                this.lastReviewId,
                this.sortById,
                this.petId,
                memberId
        );
    }

}
