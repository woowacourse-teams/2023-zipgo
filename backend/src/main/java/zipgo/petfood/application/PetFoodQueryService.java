package zipgo.petfood.application;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.HasStandard;
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

    public List<PetFood> getPetFoods(
            List<String> brandsName,
            List<String> nutrientStandards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    ) {
        return petFoodRepository.findAll().stream()
                .filter(petFood -> isValidStandard(nutrientStandards, petFood.getHasStandard())
                        && isContainBrandsName(brandsName, petFood.getBrand().getName())
                        && isContainMainIngredients(petFood.getPrimaryIngredients(), primaryIngredientList)
                        && isContainFunctionalities(petFood.getFunctionality(), functionalityList))
                .collect(Collectors.toList());
    }

    private boolean isContainBrandsName(List<String> brandsName, String brandName) {
        if (brandsName.isEmpty()) {
            return true;
        }
        return brandsName.contains(brandName);
    }

    private boolean isValidStandard(List<String> nutrientStandards, HasStandard hasStandard) {
        if (nutrientStandards.isEmpty()) {
            return true;
        }
        boolean isValid = true;
        for (String nutrientStandard : nutrientStandards) {
            if (nutrientStandard.equals("유럽") && !hasStandard.getEurope()) {
                isValid = false;
            }
            if (nutrientStandard.equals("미국") && !hasStandard.getUnitedStates()) {
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean isContainMainIngredients(List<String> primaryIngredients, List<String> primaryIngredientList) {
        if (primaryIngredientList.isEmpty()) {
            return true;
        }
        return primaryIngredientList.stream()
                .anyMatch(it -> primaryIngredients.contains(it));
    }

    private boolean isContainFunctionalities(List<String> functionalities, List<String> functionalityList) {
        if (functionalityList.isEmpty()) {
            return true;
        }
        return functionalities.stream()
                .anyMatch(it -> functionalities.contains(it));
    }

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
