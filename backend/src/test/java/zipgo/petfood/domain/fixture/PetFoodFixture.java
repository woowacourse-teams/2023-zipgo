package zipgo.petfood.domain.fixture;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

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
                .hasStandard(HasStandard.builder().build())
                .brand(brand)
                .build();
    }

    public static PetFood 아이디가_있는_식품(Long id, Brand brand) {
        return PetFood.builder()
                .id(id)
                .name("[고집] 돌아온 배배")
                .imageUrl("https://github.com/woowacourse-teams/2023-zipgo")
                .purchaseLink("https://avatars.githubusercontent.com/u/94087228?v=4")
                .brand(brand)
                .hasStandard(HasStandard.builder().build())
                .build();
    }

    public static PetFood 식품_초기화(Brand brand) {
        return PetFood.builder()
                .name("이름이다")
                .imageUrl("imageUrl")
                .purchaseLink("purchaseLink")
                .brand(brand)
                .primaryIngredients(List.of("닭고기", "쌀", "귀리", "보리"))
                .hasStandard(HasStandard.builder().build())
                .build();
    }

}
