package zipgo.petfood.domain.repository;

import java.util.List;
import zipgo.petfood.domain.PetFood;

public interface PetFoodQueryRepository {

    List<PetFood> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> functionalityList,
            List<String> primaryIngredientList,
            Long lastPetFoodId,
            int size
    );

    Long getCount(
            List<String> brandsName,
            List<String> standards,
            List<String> functionalityList,
            List<String> primaryIngredientList
    );

    PetFood findPetFoodWithReviewsByPetFoodId(Long petFoodId);

}
