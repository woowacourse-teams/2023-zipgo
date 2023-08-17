package zipgo.review.dto.response;

import java.util.List;

public record GetReviewsSummaryResponse(
        RatingSummaryElement rating,
        List<SummaryElement> tastePreference,
        List<SummaryElement> stoolCondition,
        List<SummaryElement> adverseReaction
) {

    public static GetReviewsSummaryResponse of(
            RatingSummaryElement rating,
            List<SummaryElement> tastePreference,
            List<SummaryElement> stoolCondition,
            List<SummaryElement> adverseReaction
    ) {
        return new GetReviewsSummaryResponse(rating, tastePreference, stoolCondition, adverseReaction);
    }

}
