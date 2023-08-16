package zipgo.review.dto.response;

import java.util.List;

public record GetReviewSummaryResponse(
        RatingSummaryElement rating,
        List<SummaryElement> tastePreference,
        List<SummaryElement> stoolCondition,
        List<SummaryElement> adverseReaction
) {

    public static GetReviewSummaryResponse of(
            RatingSummaryElement rating,
            List<SummaryElement> tastePreference,
            List<SummaryElement> stoolCondition,
            List<SummaryElement> adverseReaction
    ) {
        return new GetReviewSummaryResponse(rating, tastePreference, stoolCondition, adverseReaction);
    }

}
