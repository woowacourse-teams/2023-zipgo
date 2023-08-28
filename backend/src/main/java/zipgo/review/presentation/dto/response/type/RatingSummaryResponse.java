package zipgo.review.presentation.dto.response.type;

import java.util.List;

public record RatingSummaryResponse(
        double average,
        List<RatingInfoResponse> rating
) {

    public static RatingSummaryResponse of(double averageRating, List<RatingInfoResponse> ratingInfos) {
        return new RatingSummaryResponse(averageRating, ratingInfos);
    }

}
