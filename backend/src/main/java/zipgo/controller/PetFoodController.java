package zipgo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.controller.dto.GetPetFoodsResponse;
import zipgo.domain.PetFood;
import zipgo.service.PetFoodService;

import java.util.List;

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
}
