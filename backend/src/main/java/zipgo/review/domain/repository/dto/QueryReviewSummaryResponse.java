package zipgo.review.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

public record QueryReviewSummaryResponse(
        Integer rating,
        TastePreference tastePreference,
        StoolCondition stoolCondition,
        List<AdverseReactionType> adverseReactionType
) {

    @QueryProjection
    public QueryReviewSummaryResponse {
    }

}
