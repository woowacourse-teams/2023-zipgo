package zipgo.review.domain;

import lombok.Getter;
import zipgo.review.exception.TastePreferenceException;

import java.util.Arrays;

@Getter
public enum TastePreference {

    NOT_AT_ALL("전혀 안 먹어요"),
    NOT_SO_WELL("잘 안 먹어요"),
    EATS_MODERATELY("잘 먹는 편이에요"),
    EATS_VERY_WELL("정말 잘 먹어요");

    private String description;

    TastePreference(String description) {
        this.description = description;
    }

    public static TastePreference from(String tastePreference) {
        return Arrays.stream(values())
                .filter(value -> value.getDescription().equals(tastePreference))
                .findAny()
                .orElseThrow(TastePreferenceException.NotFound::new);
    }

}

