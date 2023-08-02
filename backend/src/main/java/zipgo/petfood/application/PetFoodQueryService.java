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
            List<Long> brandIds,
            List<String> nutrientStandards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    ) {
        List<PetFood> petFoods = petFoodRepository.findAll();
        for (PetFood petFood : petFoods) {
            System.out.println("petFood.getBrand().getId() = " + petFood.getBrand().getId());
            System.out.println("petFood.getHasStandard().getEurope() = " + petFood.getHasStandard().getEurope());
            System.out.println("petFood.getFunctionality().get(0) = " + petFood.getFunctionality().get(0));
            System.out.println("petFood.getPrimaryIngredients().get(0) = " + petFood.getPrimaryIngredients().get(0));
            System.out.println("=================================");
        }
        return petFoodRepository.findAll().stream()
                .filter(petFood -> isValidStandard(petFood.getId(), nutrientStandards, petFood.getHasStandard())
                        && isContainBrandIds(petFood.getId(), brandIds, petFood.getBrand().getId())
                        && isContainMainIngredients(petFood.getId(), petFood.getPrimaryIngredients(), primaryIngredientList)
                        && isContainFunctionalities(petFood.getId(), petFood.getFunctionality(), functionalityList))
                .collect(Collectors.toList());
    }

    private boolean isContainBrandIds(Long petFoodId, List<Long> brandIds, Long brandId) {
        if (brandIds.isEmpty()) {
            return true;
        }
        boolean contains = brandIds.contains(brandId);
        if (petFoodId == 1L) {
            System.out.println("result1 = " + contains);
        }
        return contains;
    }

    private static boolean isValidStandard(Long petFoodId, List<String> nutrientStandards, HasStandard hasStandard) {
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
        if (petFoodId == 1L) {
            System.out.println("result2 = " + isValid);
        }
        return isValid;
    }

    private static boolean isContainMainIngredients(Long petFoodId, List<String> primaryIngredients, List<String> primaryIngredientList) {
        System.out.println("petFoodId = " + petFoodId);
        System.out.println("primaryIngredients = " + primaryIngredients);
        System.out.println("primaryIngredientList = " + primaryIngredientList);
        System.out.println();
        if (primaryIngredientList.isEmpty()) {
            return true;
        }
        List<String> allPrimaryIngredients = primaryIngredients.stream()
                .map(primaryIngredient -> asList(primaryIngredient.split(",")))
                .flatMap(Collection::stream)
                .toList();

        boolean result = primaryIngredientList.stream()
                .anyMatch(it -> allPrimaryIngredients.contains(it));
        if (petFoodId == 1L) {
            System.out.println("result3 = " + result);
        }
        return result;
    }

    private static boolean isContainFunctionalities(Long petFoodId, List<String> functionalities, List<String> functionalityList) {
        if (functionalityList.isEmpty()) {
            return true;
        }
        List<String> allFunctionalities = functionalities.stream()
                .map(it -> asList(it.split(",")))
                .flatMap(Collection::stream)
                .toList();

        boolean result = functionalities.stream()
                .anyMatch(it -> allFunctionalities.contains(it));
        if (petFoodId == 1L) {
            System.out.println("result4 = " + result);
        }
        return result;
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
