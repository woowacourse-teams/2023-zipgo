package zipgo.petfood.presentation;

import io.jsonwebtoken.lang.Strings;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.petfood.application.PetFoodQueryService;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.presentation.dto.FilterMetadataResponse;
import zipgo.petfood.presentation.dto.FilterRequest;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;
import zipgo.petfood.presentation.dto.GetPetFoodsResponse;

import static java.net.URLDecoder.decode;
import static java.util.Collections.EMPTY_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet-foods")
public class PetFoodController {

    private final PetFoodQueryService petFoodQueryService;

    @GetMapping
    public ResponseEntity<GetPetFoodsResponse> getPetFoods(
            @RequestParam(required = false) String brands,
            @RequestParam(required = false) String nutritionStandards,
            @RequestParam(required = false) String functionalities,
            @RequestParam(required = false) String mainIngredients,
            @RequestParam(required = false) Long lastPetFoodId,
            @RequestParam(defaultValue = "20", required = false) int size
    ) throws UnsupportedEncodingException {
        FilterRequest filterDto = FilterRequest.of(
                convertStringsToCollection(brands),
                convertStringsToCollection(nutritionStandards),
                convertStringsToCollection(mainIngredients),
                convertStringsToCollection(functionalities)
        );
        List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(filterDto, lastPetFoodId, size);
        Long count = petFoodQueryService.getPetFoodsCountByFilters(filterDto);
        return ResponseEntity.ok(GetPetFoodsResponse.from(count, petFoods));
    }

    private List<String> convertStringsToCollection(String values) throws UnsupportedEncodingException {
        if (Strings.hasText(values)) {
            String decodedValues = decode(values, "UTF-8");
            return Arrays.asList(decodedValues.split(","));
        }
        return EMPTY_LIST;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetFoodResponse> getPetFood(@PathVariable Long id) {
        GetPetFoodResponse petFoodResponse = petFoodQueryService.getPetFoodResponse(id);
        return ResponseEntity.ok(petFoodResponse);
    }

    @GetMapping("/filters")
    public ResponseEntity<FilterMetadataResponse> getFilterMetadata() {
        FilterResponse filterResponse = petFoodQueryService.getMetadataForFilter();
        FilterMetadataResponse filterMetadataResponse = FilterMetadataResponse.of(filterResponse);
        return ResponseEntity.ok().body(filterMetadataResponse);
    }

}
