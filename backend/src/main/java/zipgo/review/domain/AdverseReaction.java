package zipgo.review.domain;

public enum AdverseReaction {

    MATTED_FUR("털이 푸석해요"),
    EATS_AND_VOMITS("먹고 토해요"),
    TEARY_EYED("눈물이 나요"),
    SCRATCHES_BODY("촉촉 말랑해요"),
    LICKS_PAWS("발을 핥아요");

    private String value;

    AdverseReaction(String value) {
        this.value = value;
    }

}
