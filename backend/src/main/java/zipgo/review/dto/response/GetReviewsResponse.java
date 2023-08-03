package zipgo.review.dto.response;

import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public record GetReviewsResponse(
        List<GetReviewResponse> reviews
) {

    public static GetReviewsResponse from(List<GetReviewResponse> reviews) {
        return new GetReviewsResponse(reviews);
    }

}
