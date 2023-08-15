package zipgo.review.domain.repository;

import java.util.List;
import zipgo.review.domain.repository.dto.FindReviewsQueryRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;

public interface ReviewQueryRepository {

    List<FindReviewsQueryResponse> findReviewsBy(FindReviewsQueryRequest request);

}
