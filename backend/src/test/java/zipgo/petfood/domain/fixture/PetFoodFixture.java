package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

public class PetFoodFixture {

    public static PetFood 키워드_없이_식품_초기화() {
        return PetFood.builder()
                .name("[고집] 돌아온 배배")
                .imageUrl("https://github.com/woowacourse-teams/2023-zipgo")
                .purchaseLink("https://avatars.githubusercontent.com/u/94087228?v=4")
                .build();
    }

    public static PetFood 키워드_있는_식품_초기화(Keyword keyword) {
        return PetFood.builder()
                .name("[고집] 돌아온 배배")
                .imageUrl("https://github.com/woowacourse-teams/2023-zipgo")
                .purchaseLink("https://avatars.githubusercontent.com/u/94087228?v=4")
                .keyword(keyword)
                .build();
    }

}
