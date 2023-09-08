package zipgo.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodFunctionality;
import zipgo.petfood.domain.PetFoodPrimaryIngredient;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.admin.dto.FunctionalityCreateRequest;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.admin.dto.PrimaryIngredientCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;
    private final PetFoodRepository petFoodRepository;

    public Long createBrand(BrandCreateRequest request, String imageUrl) {
        Brand brand = request.toEntity(imageUrl);
        return brandRepository.save(brand).getId();
    }

    public Long createFunctionality(FunctionalityCreateRequest request) {
        Functionality functionality = request.toEntity();
        return functionalityRepository.save(functionality).getId();
    }

    public Long createPrimaryIngredient(PrimaryIngredientCreateRequest request) {
        PrimaryIngredient primaryIngredient = request.toEntity();
        return primaryIngredientRepository.save(primaryIngredient).getId();
    }

    public Long createPetFood(PetFoodCreateRequest request, String imageUrl) {
        Brand brand = brandRepository.getById(request.brandId());
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
