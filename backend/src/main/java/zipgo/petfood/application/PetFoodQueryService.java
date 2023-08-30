package zipgo.petfood.application;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodEffect;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.infra.persist.PetFoodQueryRepositoryImpl;
import zipgo.petfood.presentation.dto.FilterRequest;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;

import static zipgo.petfood.domain.type.PetFoodOption.FUNCTIONALITY;
import static zipgo.petfood.domain.type.PetFoodOption.PRIMARY_INGREDIENT;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryService {

    private final PetFoodRepository petFoodRepository;
    private final PetFoodQueryRepositoryImpl petFoodQueryRepository;
    private final BrandRepository brandRepository;

    public List<PetFood> getPetFoodsByFilters(
            FilterRequest request,
            Long lastPetFoodId,
            int size
    ) {
        return petFoodQueryRepository.findPagingPetFoods(
                request.brands(),
                request.nutritionStandards(),
                getConcatList(request.functionalities(), request.mainIngredients()),
                lastPetFoodId,
                size
        );
    }

    public GetPetFoodResponse getPetFoodResponse(Long id) {
        PetFood petfood = petFoodRepository.getById(id);
        return GetPetFoodResponse.of(petfood, petfood.calculateRatingAverage(), petfood.countReviews());
    }

    public Long getPetFoodsCountByFilters(FilterRequest request) {
        return petFoodQueryRepository.getCount(
                request.brands(),
                request.nutritionStandards(),
                getConcatList(request.functionalities(), request.mainIngredients())
        );
    }

    public FilterResponse getMetadataForFilter() {
        List<Brand> brands = brandRepository.findAll();
        List<PetFoodEffect> functionalities = petFoodQueryRepository.findPetFoodEffectsBy(FUNCTIONALITY);
        List<PetFoodEffect> primaryIngredients = petFoodQueryRepository.findPetFoodEffectsBy(PRIMARY_INGREDIENT);
        return FilterResponse.of(brands, functionalities, primaryIngredients);
    }

    private static List<String> getConcatList(List<String> firstList, List<String> secondList) {
        return Stream.concat(
                        firstList.stream(),
                        secondList.stream())
                .toList();
    }

}
