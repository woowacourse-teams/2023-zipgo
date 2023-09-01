package zipgo.review.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import zipgo.pet.domain.Pet;
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

        @Size(max = 255, message = "255자 미만의 메시지를 작성해주세요.")
        String comment,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String tastePreference,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String stoolCondition,
        List<String> adverseReactions
) {

    public Review toEntity(Pet pet, PetFood petFood) {
        return Review.builder()
                .pet(pet)
                .petFood(petFood)
                .rating(rating)
                .comment(comment)
                .weight(pet.getWeight())
                .stoolCondition(StoolCondition.from(stoolCondition))
                .tastePreference(TastePreference.from(tastePreference))
                .build();
    }

}
