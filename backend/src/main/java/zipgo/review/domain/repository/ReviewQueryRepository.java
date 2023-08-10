package zipgo.review.domain.repository;

import java.util.List;
import zipgo.review.domain.Review;

public interface ReviewQueryRepository {

    List<Review> findAllByPetFoodId(Long petFoodId, int size, Long cursor);

}
