package zipgo.petfood.domain.repository;

import java.util.List;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.dto.FilteredPetFoodResponse;

public interface PetFoodQueryRepository {

    List<FilteredPetFoodResponse> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            int size
    );

    Long getCount(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    );

    PetFood findPetFoodWithReviewsByPetFoodId(Long petFoodId);

}
