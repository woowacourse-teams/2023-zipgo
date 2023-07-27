package zipgo.review.domain;

public enum StoolCondition {

    UNCERTAIN("잘 모르겠어요"),
    HARD("딱딱해요"),
    DIARRHEA("설사를 해요"),
    SOFT_MOIST("촉촉 말랑해요");

    private String value;

    StoolCondition(String value) {
        this.value = value;
    }

}
