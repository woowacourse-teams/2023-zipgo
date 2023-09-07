package zipgo.review.dto.response;

import java.util.List;
import zipgo.review.dto.response.type.AdverseReactionResponse;
import zipgo.review.dto.response.type.StoolConditionResponse;
import zipgo.review.dto.response.type.TastePreferenceResponse;
import zipgo.review.dto.response.type.RatingSummaryResponse;

public record GetReviewsSummaryResponse(
        RatingSummaryResponse rating,
        List<TastePreferenceResponse> tastePreference,
        List<StoolConditionResponse> stoolCondition,
        List<AdverseReactionResponse> adverseReaction
) {

    public static GetReviewsSummaryResponse of(
            RatingSummaryResponse rating,
            List<TastePreferenceResponse> tastePreference,
            List<StoolConditionResponse> stoolCondition,
            List<AdverseReactionResponse> adverseReaction
    ) {
        return new GetReviewsSummaryResponse(rating, tastePreference, stoolCondition, adverseReaction);
    }

}
