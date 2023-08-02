package zipgo.petfood.domain.fixture;

import static zipgo.petfood.domain.PrimaryIngredients.builder;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredients;

public class PetFoodFixture {

    public static PetFood 키워드_없이_식품_초기화(Brand brand) {
        return PetFood.builder()
                .name("[고집] 돌아온 배배")
                .imageUrl("https://github.com/woowacourse-teams/2023-zipgo")
                .purchaseLink("https://avatars.githubusercontent.com/u/94087228?v=4")
                .brand(brand)
                .hasStandard(HasStandard.builder().build())
                .build();
    }

    public static PetFood 키워드_있는_식품_초기화(Keyword keyword, Brand brand) {
        return PetFood.builder()
                .name("[고집] 돌아온 배배")
                .imageUrl("https://github.com/woowacourse-teams/2023-zipgo")
                .purchaseLink("https://avatars.githubusercontent.com/u/94087228?v=4")
                .keyword(keyword)
                .functionality(new Functionality(List.of("튼튼,짱짱")))
                .primaryIngredients(new PrimaryIngredients(List.of("닭고기,쌀", "말미잘")))
                .hasStandard(HasStandard.builder().build())
                .brand(brand)
                .build();
    }

    public static PetFood 식품_초기화(Brand brand) {
        return PetFood.builder()
                .name("이름이다")
                .imageUrl("imageUrl")
                .purchaseLink("purchaseLink")
                .brand(brand)
                .primaryIngredients(builder().primaryIngredients(List.of("닭고기", "쌀", "귀리", "보리")).build())
                .hasStandard(HasStandard.builder().build())
                .build();
    }

}
