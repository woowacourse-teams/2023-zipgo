package zipgo.domain.fixture;

import zipgo.domain.Keyword;
import zipgo.domain.PetFood;

public class PetFoodFixture {
    public static final PetFood 키워드가_없이_초기화된_식품 = new PetFood("[고집] 돌아온 배배",
            "https://github.com/woowacourse-teams/2023-zipgo",
            "https://avatars.githubusercontent.com/u/94087228?v=4");
    public static final PetFood 반려동물_식품_2 = new PetFood("[고집] 갈비 맛 모밀",
            "https://github.com/woowacourse-teams/2023-zipgo",
            "https://avatars.githubusercontent.com/u/76938931?v=4");
    public static final PetFood 다이어트_키워드_반려동물_식품 = 키워드가_없이_초기화된_식품;
    public static final PetFood 키워드가_없는_반려동물_식품 = 반려동물_식품_2;

    public static PetFood 키워드가_있는_반려동물_식품(Keyword keyword) {
        return new PetFood("[고집] 돌아온 배배",
                "https://github.com/woowacourse-teams/2023-zipgo",
                "https://avatars.githubusercontent.com/u/94087228?v=4", keyword);
    }
}
