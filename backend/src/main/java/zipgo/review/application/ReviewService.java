package zipgo.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews(Long petFoodId) {
        return reviewRepository.findAllByPetFoodId(petFoodId);
    }

}
