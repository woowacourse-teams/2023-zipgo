package zipgo.review.fixture;

import zipgo.review.domain.AdverseReaction;

import static zipgo.review.domain.type.AdverseReactionName.TEARS;
import static zipgo.review.domain.type.AdverseReactionName.VOMITING;

public class AdverseReactionFixture {

    public static AdverseReaction 눈물_이상반응() {
        return AdverseReaction.builder()
                .adverseReactionName(TEARS)
                .build();
    }

    public static AdverseReaction 먹고_토_이상반응() {
        return AdverseReaction.builder()
                .adverseReactionName(VOMITING)
                .build();
    }

}
