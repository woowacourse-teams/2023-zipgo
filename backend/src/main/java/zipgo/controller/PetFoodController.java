package zipgo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.controller.dto.GetPetFoodsResponse;
import zipgo.controller.dto.Response;
import zipgo.domain.PetFood;
import zipgo.service.PetFoodService;

@RestController
@RequestMapping("/pet-foods")
@RequiredArgsConstructor
public class PetFoodController {

    private final PetFoodService petFoodService;

    @GetMapping
    public Response<GetPetFoodsResponse> getPetFoods(@RequestParam(required = false) String keyword) {
        List<PetFood> petFoods = getPetFoodBy(keyword);
        return new Response<>(GetPetFoodsResponse.from(petFoods));
    }

    private List<PetFood> getPetFoodBy(final String keyword) {
        if (keyword == null) {
            return petFoodService.getAllPetFoods();
        }
        return petFoodService.getPetFoodHaving(keyword);
    }
}
