package zipgo.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.review.domain.Review;
import zipgo.review.exception.ReviewException;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ReviewException.NotFound(id));
    }

}
