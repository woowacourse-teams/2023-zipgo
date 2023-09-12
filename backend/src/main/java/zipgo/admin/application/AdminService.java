package zipgo.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.FunctionalityCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;
import zipgo.admin.dto.PetFoodUpdateRequest;
import zipgo.admin.dto.PrimaryIngredientCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodFunctionality;
import zipgo.petfood.domain.PetFoodPrimaryIngredient;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

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
            petFood.addPetFoodPrimaryIngredient(
                    PetFoodPrimaryIngredient.builder().petFood(petFood).primaryIngredient(primaryIngredient).build());
        }
    }

    private void addFunctionalities(PetFoodCreateRequest request, PetFood petFood) {
        List<Functionality> functionalities = request.functionalityIds().stream()
                .map(functionalityRepository::getById)
                .toList();
        for (Functionality functionality : functionalities) {
            petFood.addPetFoodFunctionality(
                    PetFoodFunctionality.builder().petFood(petFood).functionality(functionality).build());
        }
    }

    public void updatePetFood(Long petFoodId, PetFoodUpdateRequest request) {
        PetFood petFood = petFoodRepository.getById(petFoodId);
        petFood.updatePetFood(request.petFoodName(), request.euStandard(), request.usStandard(), request.imageUrl());
        Brand brand = brandRepository.getByName(request.brandName());
        petFood.updateBrand(brand);

        if (request.functionalities() != null) {
            changeFunctionality(request, petFood);
        }
        if (request.primaryIngredients() != null) {
            changePrimaryIngredient(request, petFood);
        }
    }

    private void changeFunctionality(PetFoodUpdateRequest request, PetFood petFood) {
        List<Functionality> functionalities = request.functionalities().stream()
                .map(functionalityName -> functionalityRepository.getByName(functionalityName))
                .toList();
        for (Functionality functionality : functionalities) {
            PetFoodFunctionality petFoodFunctionality = PetFoodFunctionality.builder()
                    .functionality(functionality)
                    .petFood(petFood)
                    .build();
            petFoodFunctionality.changeRelations(petFood, functionality);
        }
    }

    private void changePrimaryIngredient(PetFoodUpdateRequest request, PetFood petFood) {
        List<PrimaryIngredient> primaryIngredients = request.primaryIngredients().stream()
                .map(functionalityName -> primaryIngredientRepository.getByName(functionalityName))
                .toList();
        for (PrimaryIngredient primaryIngredient : primaryIngredients) {
            PetFoodPrimaryIngredient petFoodPrimaryIngredient = PetFoodPrimaryIngredient.builder()
                    .primaryIngredient(primaryIngredient)
                    .petFood(petFood)
                    .build();
            petFoodPrimaryIngredient.changeRelations(petFood, primaryIngredient);
        }
    }

    public void initFunctionalities(Long petFoodId) {
        PetFood petFood = petFoodRepository.getById(petFoodId);
        petFood.initFunctionalities();
    }

    public void initPrimaryIngredients(Long petFoodId) {
        PetFood petFood = petFoodRepository.getById(petFoodId);
        petFood.initPrimaryIngredients();
    }

    public void deletePetFood(Long petFoodId) {
        petFoodRepository.deleteById(petFoodId);
    }

}
