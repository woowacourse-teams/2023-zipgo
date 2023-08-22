package zipgo.review.domain.repository;

import java.util.List;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.dto.response.SummaryElement;

public interface ReviewQueryRepository {

    List<FindReviewsQueryResponse> findReviewsBy(FindReviewsFilterRequest request);

    List<Review> findReviewWithAdverseReactions(List<Long> reviewIds);

    List<ReviewHelpfulReaction> findReviewWithHelpfulReactions(List<Long> reviewIds, Long userId);

    double getReviewsAverageRating(Long petFoodId);

    List<SummaryElement> getReviewRatingsAverageDistribution(Long petFoodId);

    List<SummaryElement> getReviewTastesAverageDistribution(Long petFoodId);

    List<SummaryElement> getReviewStoolConditionAverageDistribution(Long petFoodId);

    List<SummaryElement> getReviewAdverseReactionAverageDistribution(Long petFoodId);

}
