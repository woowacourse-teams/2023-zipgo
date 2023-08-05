package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final BrandRepository brandRepository;
    private final PetFoodRepository petFoodRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;

    public List<PetFood> getPetFoodsByFilters(
            List<String> brandsName,
            List<String> nutritionStandards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    ) {
        return petFoodQueryRepository.findPetFoods(brandsName, nutritionStandards, primaryIngredientList,
                functionalityList);
    }

    public PetFood getPetFoodBy(Long id) {
        return petFoodRepository.getById(id);
    }

//    public FilterResponse getMetadataForFilter() {
//        return null;
//    }

}
