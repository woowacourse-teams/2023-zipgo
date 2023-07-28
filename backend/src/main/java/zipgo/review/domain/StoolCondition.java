package zipgo.review.domain;

import lombok.Getter;
import zipgo.review.exception.StoolConditionException;

import java.util.Arrays;

@Getter
public enum StoolCondition {

    UNCERTAIN("잘 모르겠어요"),
    HARD("딱딱해요"),
    DIARRHEA("설사를 해요"),
    SOFT_MOIST("촉촉 말랑해요");

    private String description;

    StoolCondition(String description) {
        this.description = description;
    }

    public static StoolCondition from(String stoolCondition) {
        return Arrays.stream(values())
                .filter(value -> value.description.equals(stoolCondition))
                .findAny()
                .orElseThrow(StoolConditionException.NotFound::new);
    }

}
