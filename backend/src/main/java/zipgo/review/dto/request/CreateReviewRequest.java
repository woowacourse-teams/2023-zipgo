package zipgo.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.Review;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

public record CreateReviewRequest(
        @NotNull(message = "Null이 올 수 없습니다. 올바른 값인지 확인해주세요.")
        Long petFoodId,

        @Max(5)
        @Min(1)
        Integer rating,
        String comment,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String tastePreference,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String stoolCondition,
        List<String> adverseReactions
) {

        public Review toEntity(Member member, PetFood petFood) {
                return Review.builder()
                        .member(member)
                        .petFood(petFood)
                        .rating(rating())
                        .comment(comment)
                        .tastePreference(TastePreference.from(tastePreference()))
                        .stoolCondition(StoolCondition.from(stoolCondition()))
                        .build();
        }

}
