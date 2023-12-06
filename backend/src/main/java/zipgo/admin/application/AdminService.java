package zipgo.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.FunctionalityCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;
import zipgo.admin.dto.PetFoodUpdateRequest;
import zipgo.admin.dto.PrimaryIngredientCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
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

    private static final int EMPTY_STRING_CHECK_INDEX = 0;

    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;
    private final PetFoodRepository petFoodRepository;
    private final BreedRepository breedRepository;
    private final PetSizeRepository petSizeRepository;

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

    @CacheEvict(cacheNames = "petFoods", keyGenerator = "customKeyGenerator")
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

        updateFunctionalities(request, petFood);
        updatePrimaryIngredients(request, petFood);
    }

    private void updateFunctionalities(PetFoodUpdateRequest request, PetFood petFood) {
        petFood.initPetFoodFunctionalities();
        List<String> functionalitiesName = request.functionalities();
        if (functionalityNameIsEmpty(functionalitiesName)) {
            return;
        }
        List<Functionality> functionalities = request.functionalities().stream()
                .map(functionalityName -> functionalityRepository.getByName(functionalityName))
                .toList();
        changeFunctionalityRelations(functionalities, petFood);
    }

    private boolean functionalityNameIsEmpty(List<String> functionalitiesName) {
        if (functionalitiesName.get(EMPTY_STRING_CHECK_INDEX).equals("")) {
            return true;
        }
        return false;
    }

    private void changeFunctionalityRelations(List<Functionality> functionalities, PetFood petFood) {
        for (Functionality functionality : functionalities) {
            PetFoodFunctionality petFoodFunctionality = PetFoodFunctionality.builder()
                    .functionality(functionality)
                    .petFood(petFood)
                    .build();
            petFoodFunctionality.changeRelations(petFood, functionality);
        }
    }

    private void updatePrimaryIngredients(PetFoodUpdateRequest request, PetFood petFood) {
        petFood.initPetFoodPrimaryIngredients();
        List<String> primaryIngredientsName = request.primaryIngredients();
        if (primaryIngredientsNameIsEmpty(primaryIngredientsName)) {
            return;
        }
        List<PrimaryIngredient> primaryIngredients = request.primaryIngredients().stream()
                .map(functionalityName -> primaryIngredientRepository.getByName(functionalityName))
                .toList();
        changePrimaryIngredientRelations(primaryIngredients, petFood);
    }

    private static boolean primaryIngredientsNameIsEmpty(List<String> primaryIngredientsName) {
        if (primaryIngredientsName.get(EMPTY_STRING_CHECK_INDEX).equals("")) {
            return true;
        }
        return false;
    }

    private void changePrimaryIngredientRelations(List<PrimaryIngredient> primaryIngredients, PetFood petFood) {
        for (PrimaryIngredient primaryIngredient : primaryIngredients) {
            PetFoodPrimaryIngredient petFoodPrimaryIngredient = PetFoodPrimaryIngredient.builder()
                    .primaryIngredient(primaryIngredient)
                    .petFood(petFood)
                    .build();
            petFoodPrimaryIngredient.changeRelations(petFood, primaryIngredient);
        }
    }

    public void deletePetFood(Long petFoodId) {
        petFoodRepository.deleteById(petFoodId);
    }

    @CacheEvict(cacheNames = "breeds", key = "'allBreeds'")
    public Long createBreed(Long petSizeId, String breedName) {
        PetSize petSize = petSizeRepository.getReferenceById(petSizeId);
        Breed breed = breedRepository.save(Breed.builder().name(breedName).petSize(petSize).build());
        return breed.getId();
    }
}
