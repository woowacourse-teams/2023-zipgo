package zipgo.review.domain;

public enum TastePreference {

    NOT_AT_ALL("전혀 안 먹어요"),
    NOT_SO_WELL("잘 안 먹어요"),
    EATS_MODERATELY("잘 먹는 편이에요"),
    EATS_VERY_WELL("정말 잘 먹어요");

    private String value;

    TastePreference(String value) {
        this.value = value;
    }

}
