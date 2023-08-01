package zipgo.review.domain.type;

import lombok.Getter;
import zipgo.review.exception.TastePreferenceException;

import java.util.Arrays;

@Getter
public enum AdverseReactionName {

    FRIZZY_FUR("털이 푸석해요"),
    VOMITING("먹고 토해요"),
    TEARS("눈물이 나요"),
    SCRATCHING("몸을 긁어요"),
    LICKING_PAWS( "발을 핥아요"),
    NONE( "없어요");

    private String description;

    AdverseReactionName(String description) {
        this.description = description;
    }

    public static AdverseReactionName from(String tastePreference) {
        return Arrays.stream(values())
                .filter(value -> value.getDescription().equals(tastePreference))
                .findAny()
                .orElseThrow(TastePreferenceException.NotFound::new);
    }

}

