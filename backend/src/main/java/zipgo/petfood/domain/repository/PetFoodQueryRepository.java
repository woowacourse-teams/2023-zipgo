package zipgo.petfood.domain.repository;

import java.util.List;
import zipgo.petfood.domain.PetFood;

public interface PetFoodQueryRepository {

    List<PetFood> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> petFoodEffects,
            Long lastPetFoodId,
            int size
    );

    Long getCount(
            List<String> brandsName,
            List<String> standards,
            List<String> petFoodEffects
    );

    PetFood findPetFoodWithReviewsByPetFoodId(Long petFoodId);

}
