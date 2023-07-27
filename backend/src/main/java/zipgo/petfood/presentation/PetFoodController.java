package zipgo.petfood.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.petfood.application.PetFoodService;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;
import zipgo.petfood.presentation.dto.GetPetFoodsResponse;
import zipgo.review.application.ReviewService;

@RestController
@RequestMapping("/pet-foods")
@RequiredArgsConstructor
public class PetFoodController {

    private final PetFoodService petFoodService;

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<GetPetFoodsResponse> getPetFoods(@RequestParam(required = false) String keyword) {
        List<PetFood> petFoods = getPetFoodsBy(keyword);
        return ResponseEntity.ok(GetPetFoodsResponse.from(petFoods));
    }

    private List<PetFood> getPetFoodsBy(String keyword) {
        if (keyword == null) {
            return petFoodService.getAllPetFoods();
        }
        return petFoodService.getPetFoodHaving(keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetFoodResponse> getPetFood(@PathVariable Long id) {
        PetFood foundPetFood = petFoodService.getPetFoodBy(id);
        int reviewCount = reviewService.countReviews(foundPetFood);
        double ratingAverage = reviewService.calculateRatingAverage(foundPetFood);

        return ResponseEntity.ok(GetPetFoodResponse.of(foundPetFood, ratingAverage, reviewCount));
    }

}
