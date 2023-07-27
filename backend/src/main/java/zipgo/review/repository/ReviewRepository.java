package zipgo.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.review.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>  {

    List<Review> findAllByPetFoodId(Long petFoodId);

}
