package zipgo.petfood.domain.fixture;

import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.Reviews;
import zipgo.petfood.presentation.dto.PetFoodCreateRequest;

import static zipgo.petfood.domain.PetFood.builder;

public class PetFoodFixture {

    public static PetFoodCreateRequest 식품_생성_요청(Long brandId) {
        return new PetFoodCreateRequest("식품", "link", brandId, true, false);
    }

    public static PetFood 모든_영양기준_만족_식품(Brand brand) {
        return builder()
                .name("모든 영양기준 만족 식품")
                .purchaseLink("https://github.com/wonyongChoi05")
                .imageUrl("https://github.com/wonyongChoi05/img")
                .brand(brand)
                .hasStandard(HasStandard.builder().unitedStates(true).europe(true).build())
                .reviews(new Reviews())
                .build();
    }

    public static PetFood 미국_영양기준_만족_식품(Brand brand) {
        return builder()
                .name("미국 영양기준 만족 식품")
                .purchaseLink("https://github.com/wonyongChoi05")
                .imageUrl("https://github.com/wonyongChoi05/img")
                .brand(brand)
                .hasStandard(HasStandard.builder().unitedStates(true).europe(false).build())
                .reviews(new Reviews())
                .build();
    }

    public static PetFood 유럽_영양기준_만족_식품(Brand brand) {
        return builder()
                .name("유럽 영양기준 만족 식품")
                .purchaseLink("https://github.com/wonyongChoi05")
                .imageUrl("https://github.com/wonyongChoi05/img")
                .brand(brand)
                .hasStandard(HasStandard.builder().unitedStates(false).europe(true).build())
                .reviews(new Reviews())
                .build();
    }

}
