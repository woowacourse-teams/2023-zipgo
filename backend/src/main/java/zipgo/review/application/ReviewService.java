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

    public double calculateRatingAverage(PetFood petFood) {
        List<Review> reviews = reviewRepository.findAllByPetFoodId(petFood.getId());
        return reviews.stream()
                .mapToInt(review -> review.getRatings())
                .average()
                .getAsDouble();
    }

    public int countReviews(PetFood petFood) {
        return reviewRepository.countByPetFoodId(petFood.getId());
    }

}
