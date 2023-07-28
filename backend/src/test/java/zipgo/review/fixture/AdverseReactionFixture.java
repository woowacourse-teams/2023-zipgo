package zipgo.review.fixture;

import zipgo.review.domain.AdverseReaction;

public class AdverseReactionFixture {

    public static AdverseReaction 눈물_이상반응() {
        return AdverseReaction.builder()
                .name("눈물이 나요")
                .build();
    }

    public static AdverseReaction 먹고_토_이상반응() {
        return AdverseReaction.builder()
                .name("먹고 토해요")
                .build();
    }

}
