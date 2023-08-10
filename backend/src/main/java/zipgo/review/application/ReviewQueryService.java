package zipgo.review.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.review.application.dto.GetReviewQueryDto;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.response.GetReviewsResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public GetReviewsResponse getReviews(GetReviewQueryDto dto) {
        List<Review> reviews = reviewQueryRepository.findReviewsBy(dto.petFoodId(), dto.size(), dto.lastReviewId());
        return GetReviewsResponse.from(reviews);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

}
