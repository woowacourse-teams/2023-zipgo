package zipgo.petfood.domain.repository;

import zipgo.petfood.dto.response.GetPetFoodQueryResponse;

import java.util.List;

public interface PetFoodQueryRepository {

    List<GetPetFoodQueryResponse> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            int size
    );

    Long findPetFoodsCount(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    );

}
