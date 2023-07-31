package zipgo.review.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.exception.PetFoodException;
import zipgo.review.domain.Review;
import zipgo.review.exception.ReviewException;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPetFoodId(Long petFoodId);

    int countByPetFoodId(Long petFoodId);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ReviewException.NotFound(id));
    }

}
