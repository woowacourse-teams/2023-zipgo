package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.application.dto.FilterDto;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final PetFoodRepository petFoodRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;
    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;

    public List<PetFood> getPetFoodsByFilters(
            FilterDto filterDto,
            Long lastPetFoodId,
            int size
    ) {
        return petFoodQueryRepository.findPagingPetFoods(
                filterDto.brands(),
                filterDto.nutritionStandards(),
                filterDto.mainIngredients(),
                filterDto.functionalities(),
                lastPetFoodId,
                size
        );
    }

    public GetPetFoodResponse getPetFoodResponse(Long id) {
        PetFood petfood = petFoodRepository.getById(id);

        return GetPetFoodResponse.of(petfood, petfood.calculateRatingAverage(), petfood.countReviews());
    }

    public Long getPetFoodsCountByFilters(FilterDto filterDto) {
        return petFoodQueryRepository.getCount(
                filterDto.brands(),
                filterDto.nutritionStandards(),
                filterDto.mainIngredients(),
                filterDto.functionalities()
        );
    }

    public FilterResponse getMetadataForFilter() {
        List<Brand> brands = brandRepository.findAll();
        List<String> functionalities = functionalityRepository.findDistinctFunctionalities();
        List<String> primaryIngredients = primaryIngredientRepository.findDistinctPrimaryIngredients();
        return FilterResponse.from(brands, primaryIngredients, functionalities);
    }

}
