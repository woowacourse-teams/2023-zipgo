package zipgo.review.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zipgo.review.domain.Review;
import zipgo.review.exception.ReviewException;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.adverseReactions where r.petFood.id = :petFoodId")
    List<Review> findAllByPetFoodId(Long petFoodId);

    int countByPetFoodId(Long petFoodId);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ReviewException.NotFound(id));
    }

}
