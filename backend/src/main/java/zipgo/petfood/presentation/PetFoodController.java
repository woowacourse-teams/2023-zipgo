package zipgo.petfood.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.petfood.application.PetFoodQueryService;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;
import zipgo.petfood.presentation.dto.GetPetFoodsResponse;
import zipgo.petfood.presentation.dto.PetFoodSelectRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet-foods")
public class PetFoodController {

    private final PetFoodQueryService petFoodQueryService;

    @PostMapping
    public ResponseEntity<GetPetFoodsResponse> getPetFoods(@RequestBody PetFoodSelectRequest request) {
        List<PetFood> petFoods = petFoodQueryService.getPetFoodByDynamicValue(
                request.keyword(),
                request.brand(),
                request.primaryIngredients()
        );
        return ResponseEntity.ok(GetPetFoodsResponse.from(petFoods));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetFoodResponse> getPetFood(@PathVariable Long id) {
        PetFood foundPetFood = petFoodQueryService.getPetFoodBy(id);
        int reviewCount = foundPetFood.countReviews();
        double ratingAverage = foundPetFood.calculateRatingAverage();

        return ResponseEntity.ok(GetPetFoodResponse.of(foundPetFood, ratingAverage, reviewCount));
    }

}
