package zipgo.review.presentation.dto.response.type;

import java.util.List;

public record RatingSummaryResponse(
        double average,
        List<RatingInfoResponse> rating
) {

}
