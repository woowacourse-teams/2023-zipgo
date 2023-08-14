package zipgo.review.domain.repository;

import java.util.List;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsQueryRequest;

public interface ReviewQueryRepository {

    List<Review> findReviewsBy(FindReviewsQueryRequest request);

}
