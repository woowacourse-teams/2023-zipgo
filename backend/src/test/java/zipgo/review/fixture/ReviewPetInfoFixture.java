package zipgo.review.fixture;

import zipgo.review.domain.type.ReviewPetInfo;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

public class ReviewPetInfoFixture {

    public static ReviewPetInfo 리뷰_반려동물_정보_생성(TastePreference tastePreference, StoolCondition stoolCondition) {
        return ReviewPetInfo.builder()
                .tastePreference(tastePreference)
                .stoolCondition(stoolCondition)
                .build();
    }

}
