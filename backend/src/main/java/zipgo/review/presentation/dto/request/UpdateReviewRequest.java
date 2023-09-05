package zipgo.review.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateReviewRequest(
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

}
