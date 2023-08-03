package zipgo.petfood.presentation;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.brand.domain.Brand;
import zipgo.petfood.application.PetFoodQueryService;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.presentation.dto.FilterMetadataResponse;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;
import zipgo.petfood.presentation.dto.GetPetFoodsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet-foods")
public class PetFoodController {

    private final PetFoodQueryService petFoodQueryService;

    @GetMapping
    public ResponseEntity<GetPetFoodsResponse> getPetFoods(
            @RequestParam List<Long> brandIds,
            @RequestParam List<String> nutrientStandards,
            @RequestParam List<String> functionalities,
            @RequestParam List<String> mainIngredients
            ) {
        List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                convertBrandIds(brandIds),
                convertNullCollection(nutrientStandards),
                convertNullCollection(mainIngredients),
                convertNullCollection(functionalities)
        );
        return ResponseEntity.ok(GetPetFoodsResponse.from(petFoods));
    }

    private List<Long> convertBrandIds(List<Long> brandIds) {
        if (brandIds == null) {
            brandIds = EMPTY_LIST;
        }
        return brandIds;
    }

    private List<String> convertNullCollection(List<String> collections) {
        if (collections == null) {
            collections = EMPTY_LIST;
        }
        return collections;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetFoodResponse> getPetFood(@PathVariable Long id) {
        PetFood foundPetFood = petFoodQueryService.getPetFoodBy(id);
        int reviewCount = foundPetFood.countReviews();
        double ratingAverage = foundPetFood.calculateRatingAverage();
        return ResponseEntity.ok(GetPetFoodResponse.of(foundPetFood, ratingAverage, reviewCount));
    }

    @GetMapping("/filters")
    public ResponseEntity<FilterMetadataResponse> getFilterMetadata() {
//        FilterResponse filter = petFoodQueryService.getMetadataForFilter();
        FilterMetadataResponse metadata = buildMetadata();
        return ResponseEntity.ok().body(metadata);
    }

    private FilterMetadataResponse buildMetadata() {
        return FilterMetadataResponse.of(
                FilterResponse.from(
                        List.of(Brand.builder()
                                        .id(2L)
                                        .name("오리젠")
                                        .nation("캐나다")
                                        .imageUrl(
                                                "https://orijenpetfoods.com.au/wp-content/themes/orijen/images/ORIJEN-Logo2016.png")
                                        .build(),
                                Brand.builder()
                                        .id(1L)
                                        .name("퓨리나")
                                        .nation("미국")
                                        .imageUrl(
                                                "https://www.nestle.com/sites/default/files/styles/brand_logo/public/purina-logo-square-2023.png?h=a7e6d17b&itok=k6CCv7Sr")
                                        .build(),
                                Brand.builder()
                                        .id(3L)
                                        .name("아카나")
                                        .nation("캐나다")
                                        .imageUrl("https://intl.acana.com/wp-content/themes/acana2019/img/logo.png")
                                        .build(),
                                Brand.builder()
                                        .id(4L)
                                        .name("인스팅트")
                                        .nation("미국")
                                        .imageUrl(
                                                "https://instinctpetfood.com/wp-content/uploads/2021/08/instinct-logo.jpg")
                                        .build()
                        ),
                        List.of("닭고기", "칠면조", "자연란"),
                        List.of("체중조절", "단백질풍부", "발육")
                )
        );
    }

}
