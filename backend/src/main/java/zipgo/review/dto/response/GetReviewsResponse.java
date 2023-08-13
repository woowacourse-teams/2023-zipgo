package zipgo.review.dto.response;

import java.util.List;
import zipgo.review.domain.Review;

public record GetReviewsResponse(
        List<GetReviewResponse> reviews
) {

    public static GetReviewsResponse from(List<Review> reviews) {
        return new GetReviewsResponse(reviews.stream()
                .map(GetReviewResponse::from).toList());
    }

}
