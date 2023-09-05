package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.application.BrandQueryService;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodFunctionality;
import zipgo.petfood.domain.PetFoodPrimaryIngredient;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.PetFoodCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class PetFoodService {

    private final PetFoodRepository petFoodRepository;
    private final BrandQueryService brandQueryService;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;

    public Long createPetFood(PetFoodCreateRequest request, String imageUrl) {
        Brand brand = brandQueryService.findBrand(request.brandId());
        PetFood petFood = request.toEntity(brand, imageUrl);

        addFunctionalities(request, petFood);
        addPrimaryIngredients(request, petFood);

        return petFoodRepository.save(petFood).getId();
    }

    private void addPrimaryIngredients(PetFoodCreateRequest request, PetFood petFood) {
        List<PrimaryIngredient> primaryIngredients = request.primaryIngredientIds().stream()
                .map(primaryIngredientRepository::getById)
                .toList();
        for (PrimaryIngredient primaryIngredient : primaryIngredients) {
            petFood.addPetFoodPrimaryIngredient(PetFoodPrimaryIngredient.builder().petFood(petFood).primaryIngredient(primaryIngredient).build());
        }
    }

    private void addFunctionalities(PetFoodCreateRequest request, PetFood petFood) {
        List<Functionality> functionalities = request.functionalityIds().stream()
                .map(functionalityRepository::getById)
                .toList();
        for (Functionality functionality : functionalities) {
            petFood.addPetFoodFunctionality(PetFoodFunctionality.builder().petFood(petFood).functionality(functionality).build());
        }
    }

}
