package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public List<PetFood> getPetFoodByDynamicValue(String keyword, String brand, String primaryIngredients) {
        return petFoodQueryRepository.searchPetFoodByDynamicValues(keyword, brand, primaryIngredients);
    }

    public PetFood getPetFoodBy(Long id) {
        return petFoodRepository.getById(id);
    }

}
