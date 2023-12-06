package zipgo.petfood.application;

import datadog.trace.api.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.dto.request.FilterRequest;
import zipgo.petfood.dto.response.FilterResponse;
import zipgo.petfood.dto.response.GetPetFoodQueryResponse;
import zipgo.petfood.dto.response.GetPetFoodResponse;
import zipgo.petfood.dto.response.GetPetFoodsResponse;
import zipgo.petfood.exception.PetFoodNotFoundException;
import zipgo.petfood.infra.persist.PetFoodQueryRepositoryImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final PetFoodQueryRepositoryImpl petFoodQueryRepository;
    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;

    @Cacheable(cacheNames = "petFoods", keyGenerator = "customKeyGenerator")
    public GetPetFoodsResponse getPetFoodsByFilters(FilterRequest filterDto, Long lastPetFoodId, int size) {
        return GetPetFoodsResponse.from(
                getPetFoodsCount(filterDto),
                getPagingPetFoods(filterDto, lastPetFoodId, size)
        );
    }

    @Trace(resourceName = "식품 페이징 조회")
    private List<GetPetFoodQueryResponse> getPagingPetFoods(FilterRequest filterDto, Long lastPetFoodId, int size) {
        return petFoodQueryRepository.findPagingPetFoods(
                filterDto.brands(),
                filterDto.nutritionStandards(),
                filterDto.mainIngredients(),
                filterDto.functionalities(),
                lastPetFoodId,
                size
        );
    }

    @Trace(resourceName = "식품 페이징 카운트 조회")
    private Long getPetFoodsCount(FilterRequest filterDto) {
        return petFoodQueryRepository.findPetFoodsCount(
                filterDto.brands(),
                filterDto.nutritionStandards(),
                filterDto.mainIngredients(),
                filterDto.functionalities()
        );
    }

    public GetPetFoodResponse getPetFoodResponse(Long id) {
        PetFood petfood = petFoodQueryRepository.findPetFoodWithRelations(id).orElseThrow(() -> new PetFoodNotFoundException(id));
        return GetPetFoodResponse.of(petfood, petfood.calculateRatingAverage(), petfood.countReviews());
    }

    public FilterResponse getMetadataForFilter() {
        List<Brand> brands = brandRepository.findAll();
        List<PrimaryIngredient> primaryIngredients = primaryIngredientRepository.findAll();
        List<Functionality> functionalities = functionalityRepository.findAll();
        return FilterResponse.of(brands, primaryIngredients, functionalities);
    }

}
