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
import zipgo.petfood.presentation.dto.GetPetFoodResponse.NutrientStandardResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse.PetFoodBrandResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse.ProteinSourceResponse;
import zipgo.petfood.presentation.dto.GetPetFoodsResponse;

@RestController
@RequestMapping("/pet-foods")
@RequiredArgsConstructor
public class PetFoodController {

    private final PetFoodService petFoodService;

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
        // todo: review 정보 가져오기
        return ResponseEntity.ok(
                new GetPetFoodResponse(
                        1L,
                        "오리젠 퍼피 짱짱 사료",
                        "https://www.petwarehouse.ph/22106-big_default/orijen-puppy-small-breed-dog-dry-food.jpg",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.petwarehouse.ph%2Fdog%2Forijen-puppy-small-breed-dog-dry-food.html&psig=AOvVaw18hwleUpDR86GtOcwHJGrC&ust=1690423061838000&source=images&cd=vfe&opi=89978449&ved=0CBMQjhxqFwoTCOjLmuOiq4ADFQAAAAAdAAAAABAE",
                        4.12,
                        12,
                        new ProteinSourceResponse(List.of("닭고기"), List.of("쌀", "귀리", "보리")),
                        new NutrientStandardResponse(true, false),
                        List.of("튼튼", "짱짱세짐", "다이어트"),
                        new PetFoodBrandResponse("오리젠", "미국", 1985, true, true)
                )
        );
    }

}
