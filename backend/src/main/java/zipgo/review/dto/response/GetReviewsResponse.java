package zipgo.review.dto.response;

import java.util.List;
import java.util.Map;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;


public record GetReviewsResponse(
        List<GetReviewResponse> reviews
) {

    public static GetReviewsResponse of(List<Review> reviews) {
        return new GetReviewsResponse(reviews.stream()
                .map(GetReviewResponse::from).toList());
    }

    public static GetReviewsResponse of(List<FindReviewsQueryResponse> reviews,
                                        Map<Long, List<String>> reviewIdToAdverseReactions,
                                        Map<Long, ReviewHelpfulReaction> reviewIdToHelpfulReactions) {
        List<GetReviewResponse> getReviewResponses = reviews.stream()
                .map(review -> GetReviewResponse.of(review, reviewIdToAdverseReactions.get(review.id()),
                        reviewIdToHelpfulReactions.get(review.id())))
                .toList();
        return new GetReviewsResponse(getReviewResponses);
    }

}
