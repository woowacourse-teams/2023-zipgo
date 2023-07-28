package zipgo.review.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPetFoodId(Long petFoodId);

    int countByPetFoodId(Long petFoodId);

}
