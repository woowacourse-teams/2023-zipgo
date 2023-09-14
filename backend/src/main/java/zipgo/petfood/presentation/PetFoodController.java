package zipgo.petfood.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.common.util.StringUtils;
import zipgo.petfood.application.PetFoodQueryService;
import zipgo.petfood.dto.request.FilterRequest;
import zipgo.petfood.dto.response.FilterMetadataResponse;
import zipgo.petfood.dto.response.FilterResponse;
import zipgo.petfood.dto.response.GetPetFoodResponse;
import zipgo.petfood.dto.response.GetPetFoodsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet-foods")
public class PetFoodController {

    private static final String PET_FOODS_PAGING_SIZE = "20";

    private final PetFoodQueryService petFoodQueryService;

    @GetMapping
    public ResponseEntity<GetPetFoodsResponse> getPetFoods(
            @RequestParam(required = false) String brands,
            @RequestParam(required = false) String nutritionStandards,
            @RequestParam(required = false) String functionalities,
            @RequestParam(required = false) String mainIngredients,
            @RequestParam(required = false) Long lastPetFoodId,
            @RequestParam(defaultValue = PET_FOODS_PAGING_SIZE, required = false) int size
    ) {
        FilterRequest filterRequest = FilterRequest.of(
                StringUtils.convertStringsToCollection(brands),
                StringUtils.convertStringsToCollection(nutritionStandards),
                StringUtils.convertStringsToCollection(mainIngredients),
                StringUtils.convertStringsToCollection(functionalities)
        );

        return ResponseEntity.ok(petFoodQueryService.getPetFoodsByFilters(filterRequest, lastPetFoodId, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetFoodResponse> getPetFood(@PathVariable Long id) {
        return ResponseEntity.ok(petFoodQueryService.getPetFoodResponse(id));
    }

    @GetMapping("/filters")
    public ResponseEntity<FilterMetadataResponse> getFilterMetadata() {
        FilterResponse filterResponse = petFoodQueryService.getMetadataForFilter();
        FilterMetadataResponse filterMetadataResponse = FilterMetadataResponse.of(filterResponse);
        return ResponseEntity.ok().body(filterMetadataResponse);
    }

}
