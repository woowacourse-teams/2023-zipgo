package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final PetFoodRepository petFoodRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;

    public PageImpl<PetFood> getPetFoodsByFilters(
            List<String> brandsName,
            List<String> nutritionStandards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            Pageable pageable
    ) {
        return petFoodQueryRepository.findPetFoods(brandsName, nutritionStandards, primaryIngredientList,
                functionalityList, lastPetFoodId, pageable);
    }

    public PetFood getPetFoodBy(Long id) {
        return petFoodRepository.getById(id);
    }

//    public FilterResponse getMetadataForFilter() {
//        return null;
//    }

}
