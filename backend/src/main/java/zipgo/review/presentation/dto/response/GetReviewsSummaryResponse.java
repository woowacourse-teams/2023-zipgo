package zipgo.review.presentation.dto.response;

import java.util.List;
import zipgo.review.presentation.dto.response.type.AdverseReactionResponse;
import zipgo.review.presentation.dto.response.type.RatingSummaryResponse;
import zipgo.review.presentation.dto.response.type.StoolConditionResponse;
import zipgo.review.presentation.dto.response.type.TastePreferenceResponse;

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
