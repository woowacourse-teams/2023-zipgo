package zipgo.review.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public List<Review> getAllReviews(Long petFoodId, int size, Long lastReviewId) {
        return reviewQueryRepository.findAllByPetFoodId(petFoodId, size, lastReviewId);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

}
