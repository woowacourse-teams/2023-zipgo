package zipgo.review.domain.repository;

import java.util.List;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;

public interface ReviewQueryRepository {

    List<FindReviewsQueryResponse> findReviewsBy(FindReviewsFilterRequest request);

    List<Review> findReviewWithAdverseReactions(List<Long> reviewIds);

    List<ReviewHelpfulReaction> findReviewWithHelpfulReactions(List<Long> reviewIds, Long userId);

}
