package zipgo.review.domain.type;

import java.util.Arrays;
import lombok.Getter;
import zipgo.review.exception.StoolConditionException;

@Getter
public enum StoolCondition {

    SOFT_MOIST("촉촉 말랑해요"),
    DIARRHEA("설사를 해요"),
    HARD("딱딱해요"),
    UNCERTAIN("잘 모르겠어요");

    private static final Integer PERCENTAGE = 100;

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

    public static int getDistributionPercentage(int total, long count) {
        return (int) (count * PERCENTAGE / total);
    }

}
