package zipgo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.controller.dto.GetPetFoodsResponse;
import zipgo.controller.dto.PetFoodResponse;
import zipgo.controller.dto.Response;

@RestController
@RequestMapping("/pet-foods")
public class PetFoodController {

    @GetMapping
    public Response getPetFoods(@RequestParam(required = false) String keyword) {
        GetPetFoodsResponse getPetFoodsResponse = new GetPetFoodsResponse(
                List.of(
                        new PetFoodResponse(1L, "https://avatars.githubusercontent.com/u/73161212?v=4", "갈비맛 모밀",
                                "https://github.com/parkmuhyeun"),
                        new PetFoodResponse(2L, "https://avatars.githubusercontent.com/u/94087228?v=4", "돌아온 배배",
                                "https://github.com/wonyongChoi05")
                )
        );
        return new Response(getPetFoodsResponse);
    }
}
