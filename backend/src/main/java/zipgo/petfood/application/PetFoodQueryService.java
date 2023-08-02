package zipgo.petfood.application;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.presentation.dto.FilterResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final BrandRepository brandRepository;
    private final PetFoodRepository petFoodRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;

    public List<PetFood> getPetFoodByDynamicValue(String keyword, String brand, String primaryIngredients) {
        return petFoodQueryRepository.findPetFoods(keyword, brand, primaryIngredients);
    }

    public PetFood getPetFoodBy(Long id) {
        return petFoodRepository.getById(id);
    }

    public FilterResponse getMetadataForFilter() {
        List<Brand> brands = brandRepository.findAll();

        List<String> primaryIngredients = petFoodRepository.findAllPrimaryIngredients().stream()
                .map(primaryIngredient -> asList(primaryIngredient.split(",")))
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        List<String> functionalities = petFoodRepository.findAllFunctionalities().stream()
                .map(functionality -> asList(functionality.split(",")))
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        return FilterResponse.from(brands, primaryIngredients, functionalities);
    }

}
