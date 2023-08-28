package zipgo.review.presentation.dto.response.type;

import zipgo.review.domain.type.StoolCondition;

public record StoolConditionResponse(
        String name,
        int percentage
) {

    public static StoolConditionResponse of(StoolCondition stoolCondition, int percentage) {
        return new StoolConditionResponse(stoolCondition.getDescription(), percentage);
    }

}
