package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

public class PetFoodFixture {

    public static PetFood 키워드_없이_식품_초기화() {
        return new PetFood("[고집] 돌아온 배배",
                "https://github.com/woowacourse-teams/2023-zipgo",
                "https://avatars.githubusercontent.com/u/94087228?v=4");
    }

    public static PetFood 키워드_있이_식품_초기화(Keyword keyword) {
        return new PetFood("[고집] 돌아온 배배",
                "https://github.com/woowacourse-teams/2023-zipgo",
                "https://avatars.githubusercontent.com/u/94087228?v=4",
                keyword);
    }

}
