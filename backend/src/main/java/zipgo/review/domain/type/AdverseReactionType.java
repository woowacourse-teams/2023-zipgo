package zipgo.review.domain.type;

import java.util.Arrays;
import lombok.Getter;
import zipgo.review.exception.AdverseReactionException;

@Getter
public enum AdverseReactionType {

    FRIZZY_FUR("털이 푸석해요"),
    VOMITING("먹고 토해요"),
    TEARS("눈물이 나요"),
    SCRATCHING("몸을 긁어요"),
    LICKING_PAWS("발을 핥아요"),
    NONE("없어요");

    private String description;

    AdverseReactionType(String description) {
        this.description = description;
    }

    public static AdverseReactionType from(String adverseReactionType) {
        return Arrays.stream(values())
                .filter(value -> value.getDescription().equals(adverseReactionType))
                .findAny()
                .orElseThrow(AdverseReactionException.NotFound::new);
    }

}

