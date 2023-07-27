package zipgo.review.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.Review;
import zipgo.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public double getRatingAverage(PetFood petFood) {
        List<Review> reviews = reviewRepository.findAllByPetFoodId(petFood.getId());
        int sumOfRatings = reviews.stream().mapToInt(review -> review.getRatings()).sum();
        return (double) sumOfRatings / reviews.size();
    }

    public int getReviewCount(PetFood petFood) {
        return reviewRepository.countByPetFoodId(petFood.getId());
    }

}
